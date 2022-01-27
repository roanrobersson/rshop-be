package br.com.roanrobersson.rshop.unit.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.roanrobersson.rshop.domain.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.factories.ProductFactory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long countTotalProducts;
	private long countPCGamerProducts;
	private long countCategoryOneAndTwoProducts;
	private PageRequest pageRequest;
	private Set<Long> categories;

	@BeforeEach
	void setUp() throws Exception {
		countTotalProducts = 25L;
		countPCGamerProducts = 21L;
		countCategoryOneAndTwoProducts = 3L;
		pageRequest = PageRequest.of(0, 10);
		categories = Set.of(1L, 2L);
	}

	@Test
	public void search_ReturnOnlySelectedCategory_CategoryInformed() {

		Page<Product> result = repository.search(categories, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countCategoryOneAndTwoProducts, result.getTotalElements());
	}

	@Test
	public void search_ReturnAllProducts_CategoryNotInformed() {

		Page<Product> result = repository.search(null, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}

	@Test
	public void search_ReturnAllProducts_NameIsEmpty() {
		String name = "";

		Page<Product> result = repository.search(null, name, pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}

	@Test
	public void search_ReturnProducts_NameExistsIgnoringCase() {
		String name = "pc gAMeR";

		Page<Product> result = repository.search(null, name, pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}

	@Test
	public void search_ReturnProducts_NameExists() {
		String name = "PC Gamer";

		Page<Product> result = repository.search(null, name, pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}

	@Test
	public void findProductsWithCategories_ReturnProducts() {
		List<Product> products = Arrays.asList(ProductFactory.createProduct());

		List<Product> result = repository.findProductsWithCategories(products);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(1, result.get(0).getCategories().size());
	}
}
