package br.com.roanrobersson.rshop.factory;

import java.time.Instant;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.dto.input.CategoryInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.domain.model.Category;

public class CategoryFactory {
	
	private static Instant instant = Instant.parse("2020-10-20T03:00:00Z");
	private static UUID id = UUID.fromString("753dad79-2a1f-4f5c-bbd1-317a53587518");
	
	public static Category createCategory() {
		return new Category(id, "Test category", instant, instant);
	}
	
	public static CategoryInputDTO createCategoryInputDTO() {
		return CategoryMapper.INSTANCE.toCategoryInputDTO(createCategory());
	}
}
