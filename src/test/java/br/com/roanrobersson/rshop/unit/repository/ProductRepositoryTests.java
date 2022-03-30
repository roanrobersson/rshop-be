package br.com.roanrobersson.rshop.unit.repository;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_NAME;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_NAME;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.roanrobersson.rshop.builder.CategoryBuilder;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	@Test
	public void search_ReturnOnlySelectedCategory_CategoryInformed() {
		Set<UUID> categories = Set.of(CategoryBuilder.EXISTING_ID, CategoryBuilder.ANOTHER_EXISTING_ID);

		Page<Product> result = repository.search(categories, "", PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(23, result.getTotalElements());
	}

	@Test
	public void search_ReturnAllProducts_CategoryNotInformed() {
		
		Page<Product> result = repository.search(null, "", PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(25, result.getTotalElements());
	}

	@Test
	public void search_ReturnEmpty_CategoryDoesNotExists() {
		Set<UUID> categories = Set.of(CategoryBuilder.NON_EXISTING_ID);

		Page<Product> result = repository.search(categories, "", PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void search_ReturnAllProducts_NameIsEmpty() {

		Page<Product> result = repository.search(null, "", PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(25, result.getTotalElements());
	}

	@Test
	public void search_ReturnProducts_NameExistsIgnoringCase() {

		Page<Product> result = repository.search(null, "pc gAMeR", PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(21, result.getTotalElements());
	}

	@Test
	public void search_ReturnProducts_NameExists() {

		Page<Product> result = repository.search(null, EXISTING_NAME, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(21, result.getTotalElements());
	}
	
	@Test
	public void search_ReturnProducts_NamePartiallyInformed() {

		Page<Product> result = repository.search(null, "PC G", PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(21, result.getTotalElements());
	}
	
	@Test
	public void search_ReturnEmpty_NameDoesNotExists() {

		Page<Product> result = repository.search(null, NON_EXISTING_NAME, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void findProductsWithCategories_ReturnProducts() {
		Product product = aProduct().build();
		List<Product> products = Arrays.asList(product);

		List<Product> result = repository.findWithCategories(products);

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(1, result.get(0).getCategories().size());
	}

	@Test
	public void findByIdWithCategories_ReturnProduct() {

		Optional<Product> optional = repository.findByIdWithCategories(EXISTING_ID);

		Assertions.assertNotNull(optional);
		Assertions.assertTrue(optional.isPresent());
		Assertions.assertEquals(1, optional.get().getCategories().size());
	}
}
