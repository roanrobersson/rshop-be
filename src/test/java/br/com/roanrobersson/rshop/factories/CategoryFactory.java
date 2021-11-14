package br.com.roanrobersson.rshop.factories;

import br.com.roanrobersson.rshop.dto.category.CategoryResponseDTO;
import br.com.roanrobersson.rshop.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		return new Category(1L, "Test category");
	}
	
	public static CategoryResponseDTO createCategoryDTO() {
		return new CategoryResponseDTO(createCategory());
	}
}
