package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.builder.CategoryBuilder.aCategory;
import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.CategoryService;;

/*
 * Mappers tests should not use object builders from the package
 * br.com.roanrobersson.rshop.builder, 
 * because the builders themselves use Mappers in the object construction process.
 */

@ExtendWith(SpringExtension.class)
public class ProductMapperTests {

	@InjectMocks
	private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

	@Mock
	private CategoryService categoryService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String JSON_PRODUCT = getContentFromResource("/json/correct/product.json");
	private static final String JSON_PRODUCT_INPUT = getContentFromResource("/json/correct/product-input.json");
	private static final String JSON_PRODUCT_INPUT_2 = getContentFromResource("/json/correct/product-input-2.json");
	private static final String JSON_PRODUCT_MODEL = getContentFromResource("/json/correct/product-model.json");

	@Test
	void toProductModel_returCompatibleProductModel() throws Exception {
		Product product = objectMapper.readValue(JSON_PRODUCT, Product.class);
		ProductModel expected = objectMapper.readValue(JSON_PRODUCT_MODEL, ProductModel.class);

		ProductModel result = productMapper.toModel(product);

		assertEquals(result, expected);
	}

	@Test
	void toProductInput_returnCompatibleProductInput() throws Exception {
		Product product = objectMapper.readValue(JSON_PRODUCT, Product.class);
		ProductInput expected = objectMapper.readValue(JSON_PRODUCT_INPUT, ProductInput.class);

		ProductInput result = productMapper.toInput(product);

		assertEquals(result, expected);
	}

	@Test
	void toProduct_returnProduct_ProductInputAsArgument() throws Exception {
		ProductInput input = objectMapper.readValue(JSON_PRODUCT_INPUT, ProductInput.class);
		UUID[] categoriesIds = input.getCategories().stream().map(c -> UUID.fromString(c.getId())).toArray(UUID[]::new);
		Category category1 = aCategory().withId(categoriesIds[0]).build();
		Category category2 = aCategory().withId(categoriesIds[1]).build();
		when(categoryService.findById(category1.getId())).thenReturn(category1);
		when(categoryService.findById(category2.getId())).thenReturn(category2);

		Product result = productMapper.toProduct(input);

		assertEquals(result.getName(), input.getName());
		assertTrue(result.getCategories().stream().anyMatch(c -> c.getId().equals(category1.getId())));
		assertTrue(result.getCategories().stream().anyMatch(c -> c.getId().equals(category2.getId())));
	}

	@Test
	void update_updateProduct() throws Exception {
		ProductInput input = objectMapper.readValue(JSON_PRODUCT_INPUT_2, ProductInput.class);
		UUID categoryId = UUID.fromString(input.getCategories().iterator().next().getId());
		Category category = aCategory().withId(categoryId).build();
		Product expected = objectMapper.readValue(JSON_PRODUCT, Product.class);
		when(categoryService.findById(categoryId)).thenReturn(category);

		productMapper.update(input, expected);

		assertEquals(input.getName(), expected.getName());
		assertEquals(input.getSku(), expected.getSku());
		assertEquals(input.getPrice(), expected.getPrice());
		assertEquals(1, expected.getCategories().size());
		assertTrue(expected.getCategories().stream().anyMatch(c -> c.getId().equals(category.getId())));
	}
}
