package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.builder.CategoryBuilder.aCategory;
import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.dto.model.ProductModel;
import br.com.roanrobersson.rshop.domain.mapper.ProductMapper;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.CategoryService;;

/*
 * Mappers tests should not use object builders from the package
 * br.com.roanrobersson.rshop.builder, 
 * because the builders themselves use Mappers in the object construction process.
 */

@ExtendWith(SpringExtension.class)
class ProductMapperUT {

	@InjectMocks
	private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

	@Mock
	private CategoryService categoryService;

	private ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

	private static final String JSON_PRODUCT = getContentFromResource("/json/correct/product.json");
	private static final String JSON_PRODUCT_2 = getContentFromResource("/json/correct/product-2.json");
	private static final String JSON_PRODUCT_INPUT = getContentFromResource("/json/correct/product-input.json");
	private static final String JSON_PRODUCT_INPUT_2 = getContentFromResource("/json/correct/product-input-2.json");
	private static final String JSON_PRODUCT_MODEL = getContentFromResource("/json/correct/product-model.json");
	private static final String JSON_PRODUCT_MODEL_2 = getContentFromResource("/json/correct/product-model-2.json");

	@Test
	void toModel_ReturnCompatibleProductModel_ProductAsArgument() throws Exception {
		Product product = objectMapper.readValue(JSON_PRODUCT, Product.class);
		ProductModel expected = objectMapper.readValue(JSON_PRODUCT_MODEL, ProductModel.class);

		ProductModel actual = productMapper.toModel(product);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void toInput_ReturnCompatibleProductInput_ProductAsArgument() throws Exception {
		Product product = objectMapper.readValue(JSON_PRODUCT, Product.class);
		ProductInput expected = objectMapper.readValue(JSON_PRODUCT_INPUT, ProductInput.class);

		ProductInput actual = productMapper.toInput(product);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void toProduct_ReturnCompatibleProduct_ProductInputAsArgument() throws Exception {
		ProductInput input = objectMapper.readValue(JSON_PRODUCT_INPUT, ProductInput.class);
		Product expected = objectMapper.readValue(JSON_PRODUCT, Product.class);
		Long[] categoriesIds = input.getCategories().stream().map(c -> c.getId()).toArray(Long[]::new);
		Category category1 = aCategory().withId(categoriesIds[0]).build();
		Category category2 = aCategory().withId(categoriesIds[1]).build();
		when(categoryService.findById(categoriesIds[0])).thenReturn(category1);
		when(categoryService.findById(categoriesIds[1])).thenReturn(category2);

		Product actual = productMapper.toProduct(input);

		assertThat(actual)
				.usingRecursiveComparison()
				.ignoringFields("id", "categories", "createdAt", "updatedAt")
				.isEqualTo(expected);
		assertThat(actual.getCategories()).containsExactlyInAnyOrderElementsOf(expected.getCategories());
	}

	@Test
	void toModelPage_ReturnCompatibleProductModelPage_ProductPageAsArgument() throws Exception {
		long anyTotalItems = 500L;
		int anyPage = 20;
		int anySize = 15;
		Sort anySort = Sort.by(Direction.ASC, "any");
		Pageable anyPageable = PageRequest.of(anyPage, anySize, anySort);
		Product product1 = objectMapper.readValue(JSON_PRODUCT, Product.class);
		Product product2 = objectMapper.readValue(JSON_PRODUCT_2, Product.class);
		Page<Product> input = new PageImpl<>(List.of(product1, product2), anyPageable, anyTotalItems);
		ProductModel expectedProductModel1 = objectMapper.readValue(JSON_PRODUCT_MODEL, ProductModel.class);
		ProductModel expectedProductModel2 = objectMapper.readValue(JSON_PRODUCT_MODEL_2, ProductModel.class);

		Page<ProductModel> actual = productMapper.toModelPage(input);

		assertThat(actual.getNumber()).isEqualTo(anyPage);
		assertThat(actual.getSize()).isEqualTo(anySize);
		assertThat(actual.getSort()).usingRecursiveComparison().isEqualTo(anySort);
		assertThat(actual.getTotalElements()).isEqualTo(anyTotalItems);
		assertThat(actual.getContent()).containsExactlyInAnyOrder(expectedProductModel1, expectedProductModel2);
	}

	@Test
	void update_CorrectUpdateProduct_ProductInputAndProductAsArgument() throws Exception {
		ProductInput input = objectMapper.readValue(JSON_PRODUCT_INPUT_2, ProductInput.class);
		Long categoryId = input.getCategories().iterator().next().getId();
		Category category = aCategory().withId(categoryId).build();
		Product actual = objectMapper.readValue(JSON_PRODUCT, Product.class);
		when(categoryService.findById(categoryId)).thenReturn(category);

		productMapper.update(input, actual);

		assertThat(actual)
				.usingRecursiveComparison()
				.ignoringFields("id", "categories", "createdAt", "updatedAt")
				.isEqualTo(input);
		assertThat(actual.getCategories()).containsExactly(category);
	}
}
