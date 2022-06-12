package br.com.roanrobersson.rshop.domain.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.dto.input.id.CategoryIdInput;
import br.com.roanrobersson.rshop.domain.dto.model.ProductModel;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

	public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Autowired
	private CategoryService categoryService;

	public abstract ProductModel toModel(Product product);

	public abstract ProductInput toInput(Product product);

	public abstract Product toProduct(ProductInput productInput);

	public Page<ProductModel> toModelPage(Page<Product> products) {
		return products.map(x -> this.toModel(x));
	};

	public abstract void update(ProductInput productInput, @MappingTarget Product product);

	protected String uuidToString(UUID uuid) {
		return uuid.toString();
	}

	protected Set<UUID> categoriesToCategoriesIds(Set<Category> categories) {
		return categories.stream().map(Category::getId).collect(Collectors.toSet());
	}

	protected Set<Category> categoriesIdsToCategories(Set<CategoryIdInput> categoriesIds) {
		Set<Category> categories = new HashSet<>();
		for (CategoryIdInput input : categoriesIds) {
			UUID id = UUID.fromString(input.getId());
			Category category = categoryService.findById(id);
			categories.add(category);
		}
		return categories;
	}
}
