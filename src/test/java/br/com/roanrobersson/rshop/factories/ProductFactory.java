package br.com.roanrobersson.rshop.factories;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.domain.Category;
import br.com.roanrobersson.rshop.domain.Product;

public class ProductFactory {

	private static ModelMapper mapper = new ModelMapper();

	public static Product createProduct() {
		Set<Category> categories = Set.of(CategoryFactory.createCategory());
		Product product = new Product(1L, categories, "Product name", "Description", BigDecimal.valueOf(800),
				"https://img.com/img.png", Instant.now(), Instant.now());
		return product;
	}

	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		ProductDTO productDTO = mapper.map(product, ProductDTO.class);
		Set<Long> categories = product.getCategories().stream().map(x -> x.getId()).collect(Collectors.toSet());
		productDTO.setCategoriesIds(categories);
		return productDTO;
	}
}
