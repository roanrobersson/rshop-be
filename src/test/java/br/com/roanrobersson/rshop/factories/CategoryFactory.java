package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import br.com.roanrobersson.rshop.domain.dto.response.CategoryResponseDTO;
import br.com.roanrobersson.rshop.domain.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		return new Category(1L, "Test category", Instant.now(), Instant.now());
	}
	
	public static CategoryResponseDTO createCategoryDTO() {
		return new CategoryResponseDTO(createCategory());
	}
}
