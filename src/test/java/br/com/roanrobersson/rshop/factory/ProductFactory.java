package br.com.roanrobersson.rshop.factory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;

public class ProductFactory {

	public static Product createProduct() {
		Set<Category> categories = Set.of(CategoryFactory.createCategory());
		Product product = new Product(1L, categories, "Product name", "Description", BigDecimal.valueOf(800),
				"https://img.com/img.png", Instant.now(), Instant.now());
		return product;
	}

	public static ProductInputDTO createProductInputDTO() {
		return ProductMapper.INSTANCE.toProductInputDTO(createProduct());
	}
	
	public static ProductDTO createProductDTO() {
		return ProductMapper.INSTANCE.toProductDTO(createProduct());
	}
}
