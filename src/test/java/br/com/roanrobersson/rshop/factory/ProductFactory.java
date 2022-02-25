package br.com.roanrobersson.rshop.factory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;

public class ProductFactory {

	private static Instant instant = Instant.parse("2020-10-20T03:00:00Z");
	private static UUID id = UUID.fromString("7c4125cc-8116-4f11-8fc3-f40a0775aec7");
	
	public static Product createProduct() {
		Set<Category> categories = Set.of(CategoryFactory.createCategory());
		Product product = new Product(id, "XBSO0200", categories, "Product name", "Description", BigDecimal.valueOf(800),
				"https://img.com/img.png", instant, instant);
		return product;
	}

	public static ProductInput createProductInput() {
		return ProductMapper.INSTANCE.toProductInput(createProduct());
	}
	
	public static ProductModel createProductModel() {
		return ProductMapper.INSTANCE.toProductModel(createProduct());
	}
}
