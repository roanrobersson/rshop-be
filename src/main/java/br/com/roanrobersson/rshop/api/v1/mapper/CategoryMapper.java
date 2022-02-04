package br.com.roanrobersson.rshop.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.roanrobersson.rshop.api.v1.dto.CategoryDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.CategoryInputDTO;
import br.com.roanrobersson.rshop.domain.model.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CategoryMapper {

	public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	public abstract CategoryDTO toCategoryDTO(Category category);

	public abstract CategoryInputDTO toCategoryInputDTO(Category category);

	public abstract Category toCategory(CategoryInputDTO categoryInputDTO);

	public abstract void update(CategoryInputDTO categoryInputDTO, @MappingTarget Category category);

	public abstract void update(Category category, @MappingTarget CategoryInputDTO categoryInputDTO);
}
