package br.com.roanrobersson.rshop.domain.mapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.core.config.MapStructConfig;
import br.com.roanrobersson.rshop.domain.dto.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.dto.model.CategoryModel;
import br.com.roanrobersson.rshop.domain.model.Category;

@Mapper(config = MapStructConfig.class)
public abstract class CategoryMapper {

	public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	public abstract CategoryModel toModel(Category category);

	public abstract CategoryInput toInput(Category category);

	public abstract Category toCategory(CategoryInput categoryInput);

	public Page<CategoryModel> toModelPage(Page<Category> categories) {
		return categories.map(x -> this.toModel(x));
	};

	public Set<UUID> toIdSet(Set<Category> categories) {
		return categories.stream().map(Category::getId).collect(Collectors.toSet());
	}

	public abstract void update(CategoryInput categoryInput, @MappingTarget Category category);
}
