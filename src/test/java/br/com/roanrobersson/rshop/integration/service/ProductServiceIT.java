package br.com.roanrobersson.rshop.integration.service;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.exception.EntityNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@SpringBootTest
@Transactional
class ProductServiceIT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private static final long COUNT_TOTAL_PRODUCTS = 25L;
	private static final long COUNT_PC_GAMER_PRODUCTS = 21L;
	private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, 10);
	private static final Set<UUID> EMPTY_SET = Set.of();

	@Test
	void findAllPaged_ReturnAllProducts_CategoryNotInformed() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(COUNT_TOTAL_PRODUCTS, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnOnlySelectedCategory_CategoryInformed() {
		UUID computersCategoryId = UUID.fromString("5c2b2b98-7b72-42dd-8add-9e97a2967e11");
		long countComputersProducts = 23L;
		Set<UUID> categories = Set.of(computersCategoryId);

		Page<Product> result = service.findAllPaged(categories, "", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(countComputersProducts, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnAllProducts_NameIsEmpty() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(COUNT_TOTAL_PRODUCTS, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnProducts_NameExistsIgnoringCase() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "pc gAMeR", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(COUNT_PC_GAMER_PRODUCTS, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnProducts_NameExists() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "PC Gamer", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(COUNT_PC_GAMER_PRODUCTS, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnPage_Page0Size10() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", DEFAULT_PAGE_REQUEST);

		assertFalse(result.isEmpty());
		assertEquals(0, result.getNumber());
		assertEquals(10, result.getSize());
		assertEquals(COUNT_TOTAL_PRODUCTS, result.getTotalElements());
	}

	@Test
	void findAllPaged_ReturnEmptyPage_PageDoesNotExist() {

		Page<Product> result = service.findAllPaged(EMPTY_SET, "", PageRequest.of(50, 10));

		assertTrue(result.isEmpty());
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
