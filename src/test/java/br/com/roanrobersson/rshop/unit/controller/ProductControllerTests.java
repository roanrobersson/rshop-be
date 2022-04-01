package br.com.roanrobersson.rshop.unit.controller;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.builder.ProductBuilder;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	private final String USERNAME = "administrator@gmail.com";
	private final String PASSWORD = "12345678";

	@Test
	void findAll_ReturnPage() throws Exception {
		Product product = aProduct().build();
		PageImpl<Product> page = new PageImpl<>(List.of(product));
		when(service.findAllPaged(anySet(), anyString(), any(PageRequest.class))).thenReturn(page);

		ResultActions result = mockMvc.perform(get("/v1/products").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
	}

	@Test
	void findById_ReturnProduct_IdExist() throws Exception {
		ProductBuilder builder = aProduct().withId(EXISTING_ID).withExistingName().withExistingSKU();
		Product product = builder.build();
		ProductModel model = builder.buildModel();
		String expectedJsonBody = objectMapper.writeValueAsString(model);
		when(service.findById(EXISTING_ID)).thenReturn(product);

		ResultActions result = mockMvc
				.perform(get("/v1/products/{productId}", EXISTING_ID).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(content().json(expectedJsonBody));
	}

	@Test
	void findById_ReturnNotFound_IdDoesNotExist() throws Exception {
		when(service.findById(NON_EXISTING_ID)).thenThrow(ProductNotFoundException.class);

		ResultActions result = mockMvc
				.perform(get("/v1/products/{productId}", NON_EXISTING_ID).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	void insert_ReturnProductModel_ValidProductInput() throws Exception {
		String accessToken = obtainAccessToken(USERNAME, PASSWORD);
		ProductBuilder builder = aProduct().withExistingId().withNonExistingName().withNonExistingSKU()
				.withExistingCategory();
		String jsonBody = objectMapper.writeValueAsString(builder.buildInput());
		String expectedJsonBody = objectMapper.writeValueAsString(builder.buildModel());
		when(service.insert(any(ProductInput.class))).thenReturn(builder.build());

		ResultActions result = mockMvc.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(content().json(expectedJsonBody));
	}

	@Test
	void insert_ReturnBadRequest_NegativePrice() throws Exception {
		String accessToken = obtainAccessToken(USERNAME, PASSWORD);
		ProductInput invalidInput = aNonExistingProduct().withInvalidPrice().buildInput();
		String jsonBody = objectMapper.writeValueAsString(invalidInput);

		ResultActions result = mockMvc.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	void update_ReturnProductModel_IdExists() throws Exception {
		String accessToken = obtainAccessToken(USERNAME, PASSWORD);
		ProductBuilder builder = aProduct().withId(EXISTING_ID).withNonExistingName().withNonExistingSKU()
				.withExistingCategory();
		String jsonBody = objectMapper.writeValueAsString(builder.buildInput());
		String expectedJsonBody = objectMapper.writeValueAsString(builder.buildModel());
		when(service.update(any(UUID.class), any(ProductInput.class))).thenReturn(builder.build());

		ResultActions result = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(content().json(expectedJsonBody));
	}

	@Test
	void update_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = obtainAccessToken(USERNAME, PASSWORD);
		ProductInput input = aProduct().withExistingCategory().buildInput();
		String jsonBody = objectMapper.writeValueAsString(input);
		when(service.update(any(UUID.class), any(ProductInput.class))).thenThrow(ProductNotFoundException.class);

		ResultActions result = mockMvc.perform(
				put("/v1/products/{productId}", NON_EXISTING_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	void delete_ReturnNoContent_IdExist() throws Exception {
		String accessToken = obtainAccessToken(USERNAME, PASSWORD);
		doNothing().when(service).delete(EXISTING_ID);

		ResultActions result = mockMvc.perform(delete("/v1/products/{productId}", EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNoContent());
	}

	@Test
	void delete_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = obtainAccessToken(USERNAME, PASSWORD);
		doThrow(ProductNotFoundException.class).when(service).delete(NON_EXISTING_ID);

		ResultActions result = mockMvc.perform(delete("/v1/products/{productId}", NON_EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	private String obtainAccessToken(String username, String password) throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", clientId);
		params.add("username", username);
		params.add("password", password);

		ResultActions result = mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(clientId, clientSecret))
						.accept("application/json;charset=UTF-8"))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}