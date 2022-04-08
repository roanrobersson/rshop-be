package br.com.roanrobersson.rshop.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.roanrobersson.rshop.api.v1.model.CategoryModel;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.model.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CategoryMapper {

	public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	public abstract CategoryModel toCategoryModel(Category category);

	public abstract CategoryInput toCategoryInput(Category category);

	public abstract Category toCategory(CategoryInput categoryInput);

	public abstract void update(CategoryInput categoryInput, @MappingTarget Category category);
}
