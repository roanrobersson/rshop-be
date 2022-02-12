package br.com.roanrobersson.rshop.api.v1.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

	public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Autowired
	private CategoryService categoryService;

	public abstract ProductDTO toProductDTO(Product product);

	public abstract ProductInputDTO toProductInputDTO(Product product);

	public abstract Product toProduct(ProductInputDTO productInputDTO);

	public abstract void update(ProductInputDTO roleInputDTO, @MappingTarget Product product);

	public abstract void update(Product product, @MappingTarget ProductInputDTO roleInputDTO);

	protected Set<UUID> categoriesToCategoriesIds(Set<Category> categories) {
		return categories.stream().map(x -> x.getId()).collect(Collectors.toSet());
	}

	protected Set<Category> categoriesIdsToCategories(Set<UUID> categoriesIds) {
		Set<Category> categories = new HashSet<>();
		for (UUID id : categoriesIds) {
			Category category = categoryService.findById(id);
			categories.add(category);
		}
		return categories;
	}
}
