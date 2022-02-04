package br.com.roanrobersson.rshop.factory;

import java.time.Instant;

import br.com.roanrobersson.rshop.api.v1.dto.input.CategoryInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.domain.model.Category;

public class CategoryFactory {
	
	public static Category createCategory() {
		return new Category(3L, "Test category", Instant.now(), Instant.now());
	}
	
	public static CategoryInputDTO createCategoryInputDTO() {
		return CategoryMapper.INSTANCE.toCategoryInputDTO(createCategory());
	}
}
