package br.com.roanrobersson.rshop.domain.mapper;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.core.config.MapStructConfig;
import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.dto.input.id.CategoryIdInput;
import br.com.roanrobersson.rshop.domain.dto.model.ProductModel;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@Mapper(config = MapStructConfig.class)
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

	protected Set<Category> categoriesIdsToCategories(Set<CategoryIdInput> categoriesIds) {
		Set<Category> categories = new HashSet<>();
		for (CategoryIdInput input : categoriesIds) {
			Long id = input.getId();
			Category category = categoryService.findById(id);
			categories.add(category);
		}
		return categories;
	}
}
