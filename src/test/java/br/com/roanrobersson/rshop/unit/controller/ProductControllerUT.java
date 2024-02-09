package br.com.roanrobersson.rshop.unit.controller;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.builder.ProductBuilder;
import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.dto.model.ProductModel;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.ProductService;
import br.com.roanrobersson.rshop.unit.UT;
import br.com.roanrobersson.rshop.util.Account;
import br.com.roanrobersson.rshop.util.ResourceUtils;
import br.com.roanrobersson.rshop.util.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled() // This class need authentication mocking
class ProductControllerUT implements UT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenUtil tokenUtil;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	private final String INVALID_ID = "TTTTTTTTTT_TTTT";
	private final Account ADMINSTRATOR = Account.ADMINSTRATOR;
	private final String JSON_PRODUCT_WITHOUT_NAME_PROPERTY = ResourceUtils
			.getContentFromResource("/json/incorrect/product-input-without-name-property.json");

	@Test
	void findAll_ReturnProductPage() throws Exception {
		Product product = aProduct().build();
		PageImpl<Product> page = new PageImpl<>(List.of(product));
		when(service.list(anySet(), anyString(), any(PageRequest.class))).thenReturn(page);

		ResultActions actual = mockMvc.perform(get("/v1/products").accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isOk());
		actual.andExpect(jsonPath("$.content").exists());
	}

	@Test
	void findById_ReturnProductModel_IdExists() throws Exception {
		ProductBuilder builder = aProduct().withId(EXISTING_ID).withExistingName().withExistingSKU();
		Product product = builder.build();
		ProductModel model = builder.buildModel();
		String expectedJsonBody = objectMapper.writeValueAsString(model);
		when(service.findById(EXISTING_ID)).thenReturn(product);

		ResultActions actual = mockMvc
				.perform(get("/v1/products/{productId}", EXISTING_ID).accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isOk());
		actual.andExpect(content().json(expectedJsonBody));
	}

	@Test
	void findById_ReturnNotFound_IdDoesNotExist() throws Exception {
		when(service.findById(NON_EXISTING_ID)).thenThrow(ProductNotFoundException.class);

		ResultActions actual = mockMvc
				.perform(get("/v1/products/{productId}", NON_EXISTING_ID).accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isNotFound());
	}

	@Test
	void findById_ReturnBadRequest_InvalidId() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);

		ResultActions actual = mockMvc
				.perform(get("/v1/products/{productId}", INVALID_ID)
						.header("Authorization", "Bearer " + accessToken)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void insert_ReturnInsertedProductModel_ValidInput() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductBuilder builder = aProduct()
				.withExistingId()
				.withNonExistingName()
				.withNonExistingSKU()
				.withExistingCategory();
		String jsonBody = objectMapper.writeValueAsString(builder.buildInput());
		String expectedJsonBody = objectMapper.writeValueAsString(builder.buildModel());
		when(service.insert(any(ProductInput.class))).thenReturn(builder.build());

		ResultActions actual = mockMvc
				.perform(post("/v1/products")
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isCreated());
		actual.andExpect(content().json(expectedJsonBody));
	}

	@Test
	void insert_ReturnBadRequest_InputWithoutNameProperty() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);

		ResultActions actual = mockMvc
				.perform(post("/v1/products")
						.header("Authorization", "Bearer " + accessToken)
						.content(JSON_PRODUCT_WITHOUT_NAME_PROPERTY)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void insert_ReturnBadRequest_NegativePrice() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductInput inputWithNegativePrice = aNonExistingProduct().withInvalidPrice().buildInput();
		String jsonBody = objectMapper.writeValueAsString(inputWithNegativePrice);

		ResultActions actual = mockMvc
				.perform(post("/v1/products")
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void insert_ReturnBadRequest_InputWithoutCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductInput inputWithoutCategories = aProduct().withoutCategories().buildInput();
		String jsonBody = objectMapper.writeValueAsString(inputWithoutCategories);

		ResultActions actual = mockMvc
				.perform(post("/v1/products")
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void insert_ReturnBadRequest_InputWithNonExistingCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductInput inputWithNonExistingCategory = aProduct().withNonExistingCategory().buildInput();
		String jsonBody = objectMapper.writeValueAsString(inputWithNonExistingCategory);

		ResultActions actual = mockMvc
				.perform(post("/v1/products")
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void update_ReturnUpdatedProductModel_IdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductBuilder builder = aProduct()
				.withId(EXISTING_ID)
				.withNonExistingName()
				.withNonExistingSKU()
				.withExistingCategory();
		String jsonBody = objectMapper.writeValueAsString(builder.buildInput());
		String expectedJsonBody = objectMapper.writeValueAsString(builder.buildModel());
		when(service.update(any(Long.class), any(ProductInput.class))).thenReturn(builder.build());

		ResultActions actual = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isOk());
		actual.andExpect(content().json(expectedJsonBody));
	}

	@Test
	void update_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductInput input = aProduct().withExistingCategory().buildInput();
		String jsonBody = objectMapper.writeValueAsString(input);
		when(service.update(any(Long.class), any(ProductInput.class))).thenThrow(ProductNotFoundException.class);

		ResultActions actual = mockMvc
				.perform(put("/v1/products/{productId}", NON_EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isNotFound());
	}

	@Test
	void update_ReturnBadRequest_InvalidId() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		String jsonBody = objectMapper.writeValueAsString(aNonExistingProduct().buildInput());

		ResultActions actual = mockMvc
				.perform(put("/v1/products/{productId}", INVALID_ID)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void update_ReturnBadRequest_InputWithoutNameProperty() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);

		ResultActions actual = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.content(JSON_PRODUCT_WITHOUT_NAME_PROPERTY)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void update_ReturnBadRequest_InputWithNonExistingCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductInput inputWithNonExistingCategory = aProduct().withNonExistingCategory().buildInput();
		String jsonBody = objectMapper.writeValueAsString(inputWithNonExistingCategory);

		ResultActions actual = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void update_ReturnBadRequest_InputWithoutCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		ProductInput inputWithoutCategories = aProduct().withoutCategories().buildInput();
		String jsonBody = objectMapper.writeValueAsString(inputWithoutCategories);

		ResultActions actual = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isBadRequest());
	}

	@Test
	void delete_ReturnNoContent_IdExist() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		doNothing().when(service).delete(EXISTING_ID);

		ResultActions actual = mockMvc
				.perform(delete("/v1/products/{productId}", EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isNoContent());
	}

	@Test
	void delete_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR);
		doThrow(ProductNotFoundException.class).when(service).delete(NON_EXISTING_ID);

		ResultActions actual = mockMvc
				.perform(delete("/v1/products/{productId}", NON_EXISTING_ID)
						.header("Authorization", "Bearer " + accessToken)
						.accept(MediaType.APPLICATION_JSON));

		actual.andExpect(status().isNotFound());
	}
}