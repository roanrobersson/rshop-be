package br.com.roanrobersson.rshop.integration.service;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_NAME;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static br.com.roanrobersson.rshop.util.ExceptionUtils.ignoreThrowseExactly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.ProductService;
import br.com.roanrobersson.rshop.integration.IT;
import br.com.roanrobersson.rshop.util.StringToUUIDSetConverter;

@SpringBootTest
@Transactional
class ProductServiceIT extends IT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private static final long COUNT_TOTAL_PRODUCTS = 25L;
	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by(Order.asc("id")));
	private static final Set<UUID> EMPTY_SET = Set.of();

	@ParameterizedTest
	@CsvFileSource(resources = "/csv/product-search-filters.csv", numLinesToSkip = 1)
	void findAllPaged_ReturnProductPage_AppliedFilters(String productName,
			@ConvertWith(StringToUUIDSetConverter.class) Set<UUID> categories, long expectedResultCount)
			throws Exception {

		Page<Product> actual = service.list(categories, productName, DEFAULT_PAGEABLE);

		assertThat(actual.getTotalElements()).isEqualTo(expectedResultCount);
	}

	@Test
	void findAllPaged_ReturnSortedPage_SortByNameAsc() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Order.asc("name")));

		Page<Product> actual = service.list(EMPTY_SET, "", pageable);

		assertThat(actual.getContent().get(0).getName()).isEqualTo("Macbook Pro");
		assertThat(actual.getContent().get(1).getName()).isEqualTo("PC Gamer");
		assertThat(actual.getContent().get(2).getName()).isEqualTo("PC Gamer Alfa");
	}

	@Test
	void findAllPaged_ReturnPage_Page0Size10() {

		Page<Product> actual = service.list(EMPTY_SET, "", DEFAULT_PAGEABLE);

		assertThat(actual.getNumber()).isEqualTo(0);
		assertThat(actual.getSize()).isEqualTo(10);
	}

	@Test
	void findAllPaged_ReturnEmptyPage_PageDoesNotExist() {

		Page<Product> actual = service.list(EMPTY_SET, "", PageRequest.of(50, 10));

		assertThat(actual).isEmpty();
	}

	@Test
	void findAllPaged_DoesNotThrowsException_ValidParameters() {

		Throwable thrown = catchThrowable(() -> {
			service.list(EMPTY_SET, "", DEFAULT_PAGEABLE);
		});

		assertThat(thrown).isNull();
	}

	@Test
	void findById_ReturnProduct_IdExist() {

		Product actual = service.findById(EXISTING_ID);

		assertThat(actual.getName()).isEqualTo(EXISTING_NAME);
	}

	@Test
	void findById_DoesNotThrowsException_IdExist() {

		Throwable thrown = catchThrowable(() -> {
			service.findById(EXISTING_ID);
		});

		assertThat(thrown).isNull();
	}

	@Test
	void findById_ThrowProductNotFoundException_IdDoesNotExist() {

		Throwable thrown = catchThrowable(() -> {
			service.findById(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(ProductNotFoundException.class);
	}

	@Test
	void insert_SaveNewProduct_InputValid() {
		ProductInput input = aNonExistingProduct().buildInput();

		Product actual = service.insert(input);

		Product product = repository.findById(actual.getId()).get();
		assertThat(actual.getName()).isEqualTo(product.getName());
	}

	@Test
	void insert_ReturnProductModel_InputValid() {
		ProductInput input = aNonExistingProduct().buildInput();

		Product actual = service.insert(input);

		assertThat(actual.getId()).isNotNull();
		assertThat(actual.getName()).isEqualTo(input.getName());
	}

	@Test
	void insert_ThrowsUniqueException_NameAlreadyInUse() {
		ProductInput input = aNonExistingProduct().withExistingName().buildInput();

		Throwable thrown = catchThrowable(() -> {
			service.insert(input);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
	}

	@Test
	void insert_ThrowsUniqueException_CategoryNonExist() {
		ProductInput input = aProduct().withNonExistingCategory().buildInput();

		Throwable thrown = catchThrowable(() -> {
			service.insert(input);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
	}

	@Test
	void update_SaveUpdatedProduct_IdExist() {
		ProductInput input = aNonExistingProduct().withExistingId().withNonExistingName().buildInput();

		service.update(EXISTING_ID, input);

		Product product = repository.findById(EXISTING_ID).get();
		assertThat(product.getName()).isEqualTo(input.getName());
	}

	@Test
	void update_ReturnUpdatedProduct_IdExist() {
		ProductInput input = aNonExistingProduct().withExistingId().withNonExistingName().buildInput();

		Product actual = service.update(EXISTING_ID, input);

		assertThat(actual.getId()).isEqualTo(EXISTING_ID);
		assertThat(actual.getName()).isEqualTo(input.getName());
	}

	@Test
	void update_ThrowProductNotFoundException_IdDoesNotExist() {
		ProductInput input = aNonExistingProduct().buildInput();

		Throwable thrown = catchThrowable(() -> {
			service.update(NON_EXISTING_ID, input);
		});

		assertThat(thrown).isExactlyInstanceOf(ProductNotFoundException.class);
	}

	@Test
	void delete_DeleteProduct_IdExists() {

		service.delete(EXISTING_ID);

		Optional<Product> optional = repository.findById(EXISTING_ID);
		assertThat(optional).isEmpty();
	}

	@Test
	void delete_DoesNotThrowException_IdExists() {

		Throwable thrown = catchThrowable(() -> {
			service.delete(EXISTING_ID);
		});

		assertThat(thrown).isNull();
	}

	@Test
	void delete_DoesNotDeleteProduct_IdDoesNotExist() throws Throwable {

		ignoreThrowseExactly(ProductNotFoundException.class, () -> service.delete(NON_EXISTING_ID));

		assertThat(repository.count()).isEqualTo(COUNT_TOTAL_PRODUCTS);
	}

	@Test
	void delete_ThrowProductNotFoundException_IdDoesNotExist() {

		Throwable thrown = catchThrowable(() -> {
			service.delete(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(ProductNotFoundException.class);
	}
}
