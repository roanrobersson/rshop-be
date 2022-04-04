package br.com.roanrobersson.rshop.unit.repository;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.util.StringToUUIDSetConverter;

@DataJpaTest
class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	@ParameterizedTest
	@CsvFileSource(resources = "/csv/product-search-filters.csv", numLinesToSkip = 1)
	void search_ReturnProducts_AppliedFilters(String productName,
			@ConvertWith(StringToUUIDSetConverter.class) Set<UUID> categories, long expectedResultCount) {
		if (categories.isEmpty()) {
			categories = null;
		}

		Page<Product> result = repository.search(categories, productName, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
		Assertions.assertEquals(expectedResultCount, result.getTotalElements());
	}

	@Test
	void findProductsWithCategories_ReturnProducts() {
		Product product = aProduct().build();
		List<Product> products = Arrays.asList(product);

		List<Product> result = repository.findWithCategories(products);

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(1, result.get(0).getCategories().size());
	}

	@Test
	void findByIdWithCategories_ReturnProduct() {

		Optional<Product> optional = repository.findByIdWithCategories(EXISTING_ID);

		Assertions.assertNotNull(optional);
		Assertions.assertTrue(optional.isPresent());
		Assertions.assertEquals(1, optional.get().getCategories().size());
	}
}
