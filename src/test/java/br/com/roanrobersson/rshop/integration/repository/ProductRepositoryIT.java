package br.com.roanrobersson.rshop.integration.repository;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.integration.IT;
import br.com.roanrobersson.rshop.util.StringToUUIDSetConverter;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryIT extends IT {

	@Autowired
	private ProductRepository repository;

	@ParameterizedTest
	@CsvFileSource(resources = "/csv/product-search-filters.csv", numLinesToSkip = 1)
	void search_ReturnProducts_AppliedFilters(String productName,
			@ConvertWith(StringToUUIDSetConverter.class) Set<UUID> categories, long expectedResultCount) {
		if (categories.isEmpty()) {
			categories = null;
		}

		Page<Product> actual = repository.search(categories, productName, PageRequest.of(0, 10));

		assertThat(actual.getTotalElements()).isEqualTo(expectedResultCount);
	}

	@Test
	void findProductsWithCategories_ReturnProducts() {
		Product product = aProduct().build();
		List<Product> products = Arrays.asList(product);

		List<Product> actual = repository.findWithCategories(products);

		assertThat(actual.get(0).getCategories().size()).isEqualTo(2);
	}

	@Test
	void findByIdWithCategories_ReturnProduct() {

		Optional<Product> actual = repository.findById(EXISTING_ID);

		assertThat(actual.get().getCategories().size()).isEqualTo(2);
	}
}
