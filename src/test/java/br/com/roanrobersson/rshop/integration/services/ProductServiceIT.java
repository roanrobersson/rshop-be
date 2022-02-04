package br.com.roanrobersson.rshop.integration.services;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	private long countPCGamerProducts;
	private long countCategoryThreeProducts;
	private PageRequest pageRequest;
	private Set<Long> emptySet;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		countTotalProducts = 25L;
		countPCGamerProducts = 21L;
		countCategoryThreeProducts = 23L;
		pageRequest = PageRequest.of(0, 10);
		emptySet = Set.of();
	}

	@Test
	public void findAllPaged_ReturnAllProducts_CategoryNotInformed() {

		Page<Product> result = service.findAllPaged(emptySet, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}

	@Test
	public void findAllPaged_ReturnOnlySelectedCategory_CategoryInformed() {

		Page<Product> result = service.findAllPaged(Set.of(3L), "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countCategoryThreeProducts, result.getTotalElements());
	}

	@Test
	public void findAllPaged_ReturnAllProducts_NameIsEmpty() {
		String name = "";

		Page<Product> result = service.findAllPaged(emptySet, name, pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}

	@Test
	public void findAllPaged_ReturnProducts_NameExistsIgnoringCase() {
		String name = "pc gAMeR";

		Page<Product> result = service.findAllPaged(emptySet, name, pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}

	@Test
	public void findAllPaged_ReturnProducts_NameExists() {
		String name = "PC Gamer";

		Page<Product> result = service.findAllPaged(emptySet, name, pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}

	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {

		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<Product> result = service.findAllPaged(emptySet, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}

	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest pageRequest = PageRequest.of(50, 10);

		Page<Product> result = service.findAllPaged(emptySet, "", pageRequest);

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

		Page<Product> result = service.findAllPaged(emptySet, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("O Senhor dos Anéis", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(2).getName());
	}

	@Test
	public void delete_DoNothing_IdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		Assertions.assertEquals(countTotalProducts - 1, repository.count());
	}

	@Test
	public void delete_ThrowResourceNotFoundException_IdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
}
