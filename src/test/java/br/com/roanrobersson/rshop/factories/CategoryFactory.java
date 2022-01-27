package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import org.modelmapper.ModelMapper;

import br.com.roanrobersson.rshop.api.v1.dto.CategoryDTO;
import br.com.roanrobersson.rshop.domain.Category;

public class CategoryFactory {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Category createCategory() {
		return new Category(3L, "Test category", Instant.now(), Instant.now());
	}
	
	public static CategoryDTO createCategoryDTO() {
		return mapper.map(createCategory(), CategoryDTO.class);
	}
}
