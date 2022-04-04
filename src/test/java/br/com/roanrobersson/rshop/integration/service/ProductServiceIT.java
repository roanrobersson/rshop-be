package br.com.roanrobersson.rshop.integration.service;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_NAME;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_NAME;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.EntityNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
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
	private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, 10);
	private static final Set<UUID> EMPTY_SET = Set.of();

	@ParameterizedTest
	@CsvFileSource(resources = "/csv/product-search-filters.csv", numLinesToSkip = 1)
	void findAllPaged_ReturnProductPage_AppliedFilters(String productName,
			@ConvertWith(StringToUUIDSetConverter.class) Set<UUID> categories, long expectedResultCount)
			throws Exception {

		Page<Product> result = service.findAllPaged(categories, productName, DEFAULT_PAGE_REQUEST);

		assertEquals(expectedResultCount, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnSortedPage_SortByName() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", PageRequest.of(0, 10, Sort.by("name")));

		assertFalse(result.isEmpty());
		assertEquals("Macbook Pro", result.getContent().get(0).getName());
		assertEquals("PC Gamer", result.getContent().get(1).getName());
		assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}

	@Test
	void findAllPaged_ReturnPage_Page0Size10() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(0, result.getNumber());
		assertEquals(10, result.getSize());
	}

	@Test
	void findAllPaged_ReturnEmptyPage_PageDoesNotExist() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", PageRequest.of(50, 10));

		assertTrue(result.isEmpty());
	}

	@Test
	void findById_ReturnProduct_IdExist() {
		UUID id = EXISTING_ID;

		Product result = service.findById(id);

		assertNotNull(result);
		assertEquals(EXISTING_NAME, result.getName());
	}

	@Test
	void findById_ThrowProductNotFoundException_IdDoesNotExist() {

		assertThrows(ProductNotFoundException.class, () -> {
			service.findById(NON_EXISTING_ID);
		});
	}

	@Test
	void insert_ReturnProductModel_InputValid() {
		ProductInput input = aNonExistingProduct().buildInput();

		Product result = service.insert(input);

		assertNotNull(result);
		assertEquals(result.getName(), input.getName());
	}

	@Test
	void insert_ThrowsBusinessException_NameAlreadyInUse() {
		ProductInput input = aNonExistingProduct().withExistingName().buildInput();

		assertThrows(BusinessException.class, () -> {
			service.insert(input);
		});
	}

	@Test
	void insert_ThrowsBusinessException_CategoryNonExist() {
		ProductInput input = aProduct().withNonExistingCategory().buildInput();

		assertThrows(BusinessException.class, () -> {
			service.insert(input);
		});
	}

	@Test
	void update_ReturnProduct_IdExist() {
		ProductInput input = aNonExistingProduct().withExistingId().withNonExistingName().buildInput();

		Product result = service.update(EXISTING_ID, input);

		assertNotNull(result);
		assertEquals(NON_EXISTING_NAME, result.getName());
	}

	@Test
	void update_ThrowBusinessException_IdDoesNotExist() {
		ProductInput input = aNonExistingProduct().buildInput();

		assertThrows(BusinessException.class, () -> {
			service.update(NON_EXISTING_ID, input);
		});
	}

	@Test
	void delete_DoNothing_IdExists() {

		assertDoesNotThrow(() -> {
			service.delete(EXISTING_ID);
		});

		assertEquals(COUNT_TOTAL_PRODUCTS - 1, repository.count());
	}

	@Test
	void delete_ThrowResourceNotFoundException_IdDoesNotExist() {

		assertThrows(EntityNotFoundException.class, () -> {
			service.delete(NON_EXISTING_ID);
		});
	}
}
