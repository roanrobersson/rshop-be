package br.com.roanrobersson.rshop.factories;

import java.math.BigDecimal;
import java.time.Instant;

import org.modelmapper.ModelMapper;

import br.com.roanrobersson.rshop.domain.dto.ProductDTO;
import br.com.roanrobersson.rshop.domain.dto.product.ProductUpdateDTO;
import br.com.roanrobersson.rshop.domain.dto.response.ProductResponseDTO;
import br.com.roanrobersson.rshop.domain.entities.Category;
import br.com.roanrobersson.rshop.domain.entities.Product;

public class ProductFactory {
	
	private static ModelMapper modelMapper = new ModelMapper();

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", BigDecimal.valueOf(800), "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, null));
		return product;
	}
	
	public static ProductDTO createProductInsertDTO() {
		ProductDTO dto = modelMapper.map(createProduct(), ProductDTO.class);
		dto.getCategoriesId().add(1L);
		return dto;
	}
	
	public static ProductUpdateDTO createProductUpdateDTO() {
		ProductUpdateDTO dto = modelMapper.map(createProduct(), ProductUpdateDTO.class);
		dto.getCategoriesId().add(1L);
		return dto;
	}
	
	public static ProductResponseDTO createProductResponseDTO(Long id) {
		ProductResponseDTO dto = modelMapper.map(createProduct(), ProductResponseDTO.class);
		dto.setId(id);
		return dto;
	}
}
