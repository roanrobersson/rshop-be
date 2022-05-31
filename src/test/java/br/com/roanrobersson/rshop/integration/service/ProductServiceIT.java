package br.com.roanrobersson.rshop.integration.service;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_NAME;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static br.com.roanrobersson.rshop.util.ExceptionUtils.ignoreThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.ProductService;
import br.com.roanrobersson.rshop.util.StringToUUIDSetConverter;

@SpringBootTest
@Transactional
class ProductServiceIT {

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

		Page<Product> result = service.list(categories, productName, DEFAULT_PAGEABLE);

		assertEquals(expectedResultCount, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnSortedPage_SortByNameAsc() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Order.asc("name")));

		Page<Product> result = service.list(EMPTY_SET, "", pageable);

		assertFalse(result.isEmpty());
		assertEquals("Macbook Pro", result.getContent().get(0).getName());
		assertEquals("PC Gamer", result.getContent().get(1).getName());
		assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}

	@Test
	void findAllPaged_ReturnPage_Page0Size10() {

		Page<Product> result = service.list(EMPTY_SET, "", DEFAULT_PAGEABLE);

		assertFalse(result.isEmpty());
		assertEquals(0, result.getNumber());
		assertEquals(10, result.getSize());
	}

	@Test
	void findAllPaged_ReturnEmptyPage_PageDoesNotExist() {

		Page<Product> result = service.list(EMPTY_SET, "", PageRequest.of(50, 10));

		assertTrue(result.isEmpty());
	}

	@Test
	void findAllPaged_DoesNotThrowsException_ValidParameters() {

		assertDoesNotThrow(() -> service.list(EMPTY_SET, "", DEFAULT_PAGEABLE));
	}

	@Test
	void findById_ReturnProduct_IdExist() {

		Product result = service.findById(EXISTING_ID);

		assertNotNull(result);
		assertEquals(EXISTING_NAME, result.getName());
	}

	@Test
	void findById_DoesNotThrowsException_IdExist() {

		assertDoesNotThrow(() -> service.findById(EXISTING_ID));
	}
	
	@Test
	void findById_ThrowProductNotFoundException_IdDoesNotExist() {

		assertThrowsExactly(ProductNotFoundException.class, () -> {
			service.findById(NON_EXISTING_ID);
		});
	}

	@Test
	void insert_SaveNewProduct_InputValid() {
		ProductInput input = aNonExistingProduct().buildInput();

		Product savedProduct = service.insert(input);

		Product product = repository.findById(savedProduct.getId()).get();
		assertEquals(input.getName(), product.getName());
	}

	@Test
	void insert_ReturnProductModel_InputValid() {
		ProductInput input = aNonExistingProduct().buildInput();

		Product result = service.insert(input);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(result.getName(), input.getName());
	}

	@Test
	void insert_ThrowsUniqueException_NameAlreadyInUse() {
		ProductInput input = aNonExistingProduct().withExistingName().buildInput();

		assertThrowsExactly(UniqueException.class, () -> {
			service.insert(input);
		});
	}

	@Test
	void insert_ThrowsUniqueException_CategoryNonExist() {
		ProductInput input = aProduct().withNonExistingCategory().buildInput();

		assertThrowsExactly(UniqueException.class, () -> {
			service.insert(input);
		});
	}

	@Test
	void update_SaveUpdatedProduct_IdExist() {
		ProductInput input = aNonExistingProduct().withExistingId().withNonExistingName().buildInput();

		service.update(EXISTING_ID, input);

		Product product = repository.findById(EXISTING_ID).get();
		assertEquals(input.getName(), product.getName());
	}

	@Test
	void update_ReturnUpdatedProduct_IdExist() {
		ProductInput input = aNonExistingProduct().withExistingId().withNonExistingName().buildInput();

		Product result = service.update(EXISTING_ID, input);

		assertNotNull(result);
		assertEquals(EXISTING_ID, result.getId());
		assertEquals(input.getName(), result.getName());
	}

	@Test
	void update_ThrowProductNotFoundException_IdDoesNotExist() {
		ProductInput input = aNonExistingProduct().buildInput();

		assertThrowsExactly(ProductNotFoundException.class, () -> {
			service.update(NON_EXISTING_ID, input);
		});
	}

	@Test
	void delete_DeleteProduct_IdExists() {

		service.delete(EXISTING_ID);
		Optional<Product> optional = repository.findById(EXISTING_ID);

		assertFalse(optional.isPresent());
	}

	@Test
	void delete_DoesNotThrowException_IdExists() {

		assertDoesNotThrow(() -> {
			service.delete(EXISTING_ID);
		});
	}

	@Test
	void delete_DoesNotDeleteProduct_IdDoesNotExist() throws Throwable {

		ignoreThrows(() -> service.delete(NON_EXISTING_ID));

		assertEquals(COUNT_TOTAL_PRODUCTS, repository.count());
	}

	@Test
	void delete_ThrowProductNotFoundException_IdDoesNotExist() {

		assertThrowsExactly(ProductNotFoundException.class, () -> {
			service.delete(NON_EXISTING_ID);
		});
	}
}
