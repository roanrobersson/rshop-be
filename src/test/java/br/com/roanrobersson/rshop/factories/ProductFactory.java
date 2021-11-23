package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import br.com.roanrobersson.rshop.dto.product.ProductInsertDTO;
import br.com.roanrobersson.rshop.dto.product.ProductResponseDTO;
import br.com.roanrobersson.rshop.dto.product.ProductUpdateDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.entities.Product;

public class ProductFactory {

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, null));
		return product;
	}
	
	public static ProductInsertDTO createProductInsertDTO() {
		Product product = createProduct();
		return new ProductInsertDTO(product);
	}
	
	public static ProductUpdateDTO createProductUpdateDTO() {
		Product product = createProduct();
		ProductUpdateDTO dto = new ProductUpdateDTO(product);
		return dto;
	}
	
	public static ProductResponseDTO createProductResponseDTO(Long id) {
		Product product = createProduct();
		ProductResponseDTO dto = new ProductResponseDTO(product);
		dto.setId(id);
		return dto;
	}
}
