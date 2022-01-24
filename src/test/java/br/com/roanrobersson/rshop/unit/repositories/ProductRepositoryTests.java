package br.com.roanrobersson.rshop.unit.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.roanrobersson.rshop.domain.Category;
import br.com.roanrobersson.rshop.domain.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.factories.ProductFactory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long countTotalProducts;
	private long countPCGamerProducts;
	private long countCategoryThreeProducts;
	private PageRequest pageRequest;
	
	@BeforeEach
	void setUp() throws Exception {
		countTotalProducts = 25L;
		countPCGamerProducts = 21L;
		countCategoryThreeProducts = 23L;
		pageRequest = PageRequest.of(0, 10);
	}

	@Test
	public void find_ReturnOnlySelectedCategory_CategoryInformed() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(3l, null));
		
		Page<Product> result = repository.search(categories, "", pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countCategoryThreeProducts, result.getTotalElements());
	}
	
	@Test
	public void find_ReturnAllProducts_CategoryNotInformed() {
		List<Category> categories = null;
		
		Page<Product> result = repository.search(categories, "", pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}	
	
	@Test
	public void find_ReturnAllProducts_NameIsEmpty() {
		String name = "";
		
		Page<Product> result = repository.search(null, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	@Test
	public void find_ReturnProducts_NameExistsIgnoringCase() {
		String name = "pc gAMeR";
		
		Page<Product> result = repository.search(null, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}
	
	@Test
	public void find_ReturnProducts_NameExists() {
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
