package br.com.roanrobersson.rshop.integration.controller;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.util.ResourceUtils;
import br.com.roanrobersson.rshop.util.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenUtil tokenUtil;

	private final String INVALID_ID = "TTTTTTTTTT_TTTT";
	private final String USERNAME = "administrator@gmail.com";
	private final String PASSWORD = "12345678";
	private final String JSON_PRODUCT_WITHOUT_NAME_PROPERTY = ResourceUtils
			.getContentFromResource("/json/incorrect/product-without-name-property.json");

	@Test
	public void findAll_ReturnSortedPage_SortByName() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/v1/products?page=0&size=12&sort=name,asc").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(25L));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
	}

	@Test
	public void findById_ReturnProductModel_IdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(get("/v1/products/{productId}", EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("name", is("The Lord of The Rings")));
	}

	@Test
	public void findById_ReturnNotFound_IdDoesNotExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(get("/v1/products/{productId}", NON_EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void findById_ReturnBadRequest_InvalidId() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(get("/v1/products/{productId}", INVALID_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void insert_ReturnProductModel_ValidInput() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aNonExistingProduct().buildInput());

		ResultActions result = mockMvc.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("name", is("Non existing name")));
	}

	@Test
	public void insert_ReturnBadRequest_InputWithoutName() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken)
				.content(JSON_PRODUCT_WITHOUT_NAME_PROPERTY).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void insert_ReturnBadRequest_InputWithoutCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aProduct().withoutCategories().buildInput());

		ResultActions result = mockMvc.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void insert_ReturnBadRequest_InputWithNonExistingCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aProduct().withNonExistingCategory().buildInput());

		ResultActions result = mockMvc.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void update_ReturnProductModel_IdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aNonExistingProduct().buildInput());

		ResultActions result = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("name", is("Non existing name")));
	}

	@Test
	public void update_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aNonExistingProduct().buildInput());

		ResultActions result = mockMvc.perform(
				put("/v1/products/{productId}", NON_EXISTING_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void update_ReturnBadRequest_InvalidId() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aNonExistingProduct().buildInput());

		ResultActions result = mockMvc
				.perform(put("/v1/products/{productId}", INVALID_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void update_ReturnBadRequest_InputWithoutName() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(put("/v1/products/{productId}", EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).content(JSON_PRODUCT_WITHOUT_NAME_PROPERTY)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void update_ReturnBadRequest_InputWithNonExistingCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aProduct().withNonExistingCategory().buildInput());

		ResultActions result = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void update_ReturnBadRequest_InputWithoutCategory() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);
		String jsonBody = objectMapper.writeValueAsString(aProduct().withoutCategories().buildInput());

		ResultActions result = mockMvc
				.perform(put("/v1/products/{productId}", EXISTING_ID).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}

	@Test
	public void delete_ReturnNoContent_IdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(delete("/v1/products/{productId}", EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNoContent());
	}

	@Test
	public void delete_ReturnNotFound_IdDoesNotExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(delete("/v1/products/{productId}", NON_EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void delete_ReturnBadRequest_InvalidId() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, USERNAME, PASSWORD);

		ResultActions result = mockMvc.perform(delete("/v1/products/{productId}", INVALID_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isBadRequest());
	}
}
