package br.com.roanrobersson.rshop.unit.mapper;

import static br.com.roanrobersson.rshop.builder.CategoryBuilder.aCategory;
import static br.com.roanrobersson.rshop.util.ResourceUtils.getContentFromResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

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

		assertEquals(expected, actual);
	}

	@Test
	void toInput_ReturnCompatibleProductInput_ProductAsArgument() throws Exception {
		Product product = objectMapper.readValue(JSON_PRODUCT, Product.class);
		ProductInput expected = objectMapper.readValue(JSON_PRODUCT_INPUT, ProductInput.class);

		ProductInput actual = productMapper.toInput(product);

		assertEquals(expected, actual);
	}

	@Test
	void toProduct_ReturnCompatibleProduct_ProductInputAsArgument() throws Exception {
		ProductInput input = objectMapper.readValue(JSON_PRODUCT_INPUT, ProductInput.class);
		UUID[] categoriesIds = input.getCategories().stream().map(c -> UUID.fromString(c.getId())).toArray(UUID[]::new);
		Category category1 = aCategory().withId(categoriesIds[0]).build();
		Category category2 = aCategory().withId(categoriesIds[1]).build();
		when(categoryService.findById(category1.getId())).thenReturn(category1);
		when(categoryService.findById(category2.getId())).thenReturn(category2);

		Product actual = productMapper.toProduct(input);

		assertEquals(input.getName(), actual.getName());
		assertTrue(actual.getCategories().stream().anyMatch(c -> c.getId().equals(category1.getId())));
		assertTrue(actual.getCategories().stream().anyMatch(c -> c.getId().equals(category2.getId())));
	}

	@Test
	void toModelPage_ReturnCompatibleProductModelPage_ProductPageAsArgument() throws Exception {
		Product product1 = objectMapper.readValue(JSON_PRODUCT, Product.class);
		Product product2 = objectMapper.readValue(JSON_PRODUCT_2, Product.class);
		long anyTotalItems = 500L;
		Pageable anyPageable = PageRequest.of(20, 15, Sort.by(Direction.ASC, "name"));
		Page<Product> input = new PageImpl<>(List.of(product1, product2), anyPageable, anyTotalItems);
		ProductModel expected1 = objectMapper.readValue(JSON_PRODUCT_MODEL, ProductModel.class);
		ProductModel expected2 = objectMapper.readValue(JSON_PRODUCT_MODEL_2, ProductModel.class);

		Page<ProductModel> actual = productMapper.toModelPage(input);

		assertEquals(expected1, actual.getContent().toArray()[0]);
		assertEquals(expected2, actual.getContent().toArray()[1]);
		assertEquals(anyPageable.getPageNumber(), actual.getNumber());
		assertEquals(anyPageable.getPageSize(), actual.getSize());
		assertEquals(anyPageable.getSort(), actual.getSort());
		assertEquals(anyTotalItems, actual.getTotalElements());
	}

	@Test
	void update_CorrectUpdateProduct_ProductInputAndProductAsArgument() throws Exception {
		ProductInput input = objectMapper.readValue(JSON_PRODUCT_INPUT_2, ProductInput.class);
		UUID categoryId = UUID.fromString(input.getCategories().iterator().next().getId());
		Category category = aCategory().withId(categoryId).build();
		Product actual = objectMapper.readValue(JSON_PRODUCT, Product.class);
		when(categoryService.findById(categoryId)).thenReturn(category);

		productMapper.update(input, actual);

		assertEquals(actual.getName(), input.getName());
		assertEquals(actual.getSku(), input.getSku());
		assertEquals(actual.getPrice(), input.getPrice());
		assertEquals(1, actual.getCategories().size());
		assertTrue(actual.getCategories().stream().anyMatch(c -> c.getId().equals(category.getId())));
	}
}
