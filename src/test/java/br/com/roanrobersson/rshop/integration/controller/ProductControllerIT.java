package br.com.roanrobersson.rshop.integration.controller;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.integration.IT;
import br.com.roanrobersson.rshop.util.Account;
import br.com.roanrobersson.rshop.util.ResourceUtils;
import br.com.roanrobersson.rshop.util.TokenUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class ProductControllerIT extends IT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenUtil tokenUtil;

	private static final String INVALID_ID = "AAAAA";
	private static final Account ADMINSTRATOR_ACCOUNT = Account.ADMINSTRATOR;
	private static final String JSON_PRODUCT_WITHOUT_NAME_PROPERTY = ResourceUtils
			.getContentFromResource("/json/incorrect/product-input-without-name-property.json");

	@Test
	void findAllPaged_ReturnOk_ValidParameters() throws Exception {
		String validURI = UriComponentsBuilder.newInstance().host("/v1/products").queryParam("page", 0)
				.queryParam("size", 15).queryParam("sort", "name,asc").queryParam("q", "tv")
				.queryParam("categories", "1,2").build().toUriString();

		ResultActions result = mockMvc.perform(get(validURI).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	void findAll_ReturnProductPageWithCorrectProductData_ValidParameters() throws Exception {
		String uri = UriComponentsBuilder.newInstance().host("/v1/products").queryParam("page", 0).queryParam("size", 1)
				.queryParam("sort", "name,asc").build().toUriString();

		ResultActions result = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content[0].id").value(3));
		result.andExpect(jsonPath("$.content[0].categories[*].id", containsInAnyOrder(3, 2)));
		result.andExpect(jsonPath("$.content[0].sku").value("NBAP14SI"));
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[0].description").isNotEmpty());
		result.andExpect(jsonPath("$.content[0].price").value(1250.00));
		result.andExpect(jsonPath("$.content[0].imgUrl").isNotEmpty());
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/csv/product-search-filters.csv", numLinesToSkip = 1)
	void findAll_ReturnProductPageWithCorrectProductCount_SpecificRequestParams(String productName, String categories,
			long expectedResultCount) throws Exception {
		String uri = UriComponentsBuilder.newInstance().host("/v1/products").queryParam("q", productName)
				.queryParam("categories", categories).queryParam("size", 1000).build().toUriString();
		System.out.println(categories);
		ResultActions result = mockMvc
				.perform(get(uri, productName, categories).accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.totalElements").exists());
		result.andExpect(jsonPath("$.totalElements").value(expectedResultCount));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content.length()").value(expectedResultCount));
	}

	@Test
	void findAll_ReturnSortedByNameAscProductPage_NoSortParameterInformmed() throws Exception {

		ResultActions result = mockMvc.perform(get("/v1/products").accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
	}

	@Test
	void findAll_ReturnSortedByNameAscProductPage_SortByNameAsc() throws Exception {
		String uri = UriComponentsBuilder.newInstance().host("/v1/products").queryParam("sort", "name,asc").build()
				.toUriString();

		ResultActions result = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
	}

	@Test
	void findAll_ReturnSortedProductPage_SortByIdDesc() throws Exception {
		String uri = UriComponentsBuilder.newInstance().host("/v1/products").queryParam("sort", "price,desc").build()
				.toUriString();

		ResultActions result = mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content[0].id").value(25));
		result.andExpect(jsonPath("$.content[1].id").value(24));
		result.andExpect(jsonPath("$.content[2].id").value(16));
	}

	@Test
	void findAllPaged_ReturnCorrectProductPageSize_Size3() throws Exception {

		ResultActions result = mockMvc.perform(get("/v1/products?size=3").accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content.length()").value(3));
		result.andExpect(jsonPath("$.pageable").exists());
		result.andExpect(jsonPath("$.pageable.pageSize").value(3));
	}

	@Test
	void findAllPaged_ReturnCorrectProductPageNumber_Page3() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/v1/products?sort=name,asc&page=3&size=3").accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isNotEmpty());
		result.andExpect(jsonPath("$.content[0].name").value("PC Gamer Hera"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer Hot"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Max"));
		result.andExpect(jsonPath("$.pageable").exists());
		result.andExpect(jsonPath("$.pageable.pageNumber").value(3));
	}

	@Test
	void findAllPaged_ReturnEmptyPage_PageNumberDoesNotExist() throws Exception {

		ResultActions result = mockMvc.perform(get("/v1/products?page=50").accept(MediaType.APPLICATION_JSON));

		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content").isEmpty());
		result.andExpect(jsonPath("$.pageable").exists());
		result.andExpect(jsonPath("$.pageable.pageNumber").value(50));
	}

	@Test
	void findById_ReturnProductModelWithCorrectData_IdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);

		ResultActions result = mockMvc.perform(get("/v1/products/{productId}", EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("id").value(1));
		result.andExpect(jsonPath("categories[*].id", containsInAnyOrder(1, 3)));
		result.andExpect(jsonPath("sku").value("TVLG32BL"));
		result.andExpect(jsonPath("name").value("Smart TV"));
		result.andExpect(jsonPath("description").isNotEmpty());
		result.andExpect(jsonPath("price").value(2190.00));
		result.andExpect(jsonPath("imgUrl").isNotEmpty());
	}

	private static Stream<Arguments> findByIdReturnSpecificHttpStatusArgumentSource() {
		return Stream.of(Arguments.of(EXISTING_ID.toString(), HttpStatus.OK),
				Arguments.of(NON_EXISTING_ID.toString(), HttpStatus.NOT_FOUND),
				Arguments.of(INVALID_ID, HttpStatus.BAD_REQUEST));
	}

	@ParameterizedTest
	@MethodSource("findByIdReturnSpecificHttpStatusArgumentSource")
	void findById_ReturnSpecificHttpStatus(String id, HttpStatus status) throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);

		ResultActions result = mockMvc.perform(get("/v1/products/{productId}", id)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(status.value()));
	}

	@Test
	void insert_ReturnInsertedProductModel_ValidInput() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);
		ProductInput validInput = aNonExistingProduct().buildInput();
		String jsonBody = objectMapper.writeValueAsString(validInput);

		ResultActions result = mockMvc
				.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(jsonPath("name", is("Non existing name")));
	}

	private static Stream<Arguments> insertReturnSpecificHttpStatusArgumentSource() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String validInput = mapper.writeValueAsString(aNonExistingProduct().buildInput());
		String inputWithNegativePrice = mapper
				.writeValueAsString(aNonExistingProduct().withInvalidPrice().buildInput());
		String inputWithoutCategory = mapper.writeValueAsString(aProduct().withoutCategories().buildInput());

		return Stream.of(Arguments.of(validInput, HttpStatus.CREATED),
				Arguments.of(JSON_PRODUCT_WITHOUT_NAME_PROPERTY, HttpStatus.BAD_REQUEST),
				Arguments.of(inputWithNegativePrice, HttpStatus.BAD_REQUEST),
				Arguments.of(inputWithoutCategory, HttpStatus.BAD_REQUEST));
	}

	@ParameterizedTest
	@MethodSource("insertReturnSpecificHttpStatusArgumentSource")
	void insert_ReturnSpecificHttpStatus(String jsonBody, HttpStatus status) throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);

		ResultActions result = mockMvc
				.perform(post("/v1/products").header("Authorization", "Bearer " + accessToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(status.value()));
	}

	@Test
	void update_ReturnUpdatedProductModel_IdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);
		String jsonBody = objectMapper.writeValueAsString(aNonExistingProduct().buildInput());

		ResultActions result = mockMvc.perform(put("/v1/products/{productId}", EXISTING_ID)
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		result.andExpect(jsonPath("name", is("Non existing name")));
	}

	private static Stream<Arguments> updateReturnSpecificHttpStatusArgumentSource() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String validInput = mapper.writeValueAsString(aNonExistingProduct().buildInput());
		String inputWithNegativePrice = mapper
				.writeValueAsString(aNonExistingProduct().withInvalidPrice().buildInput());
		String inputWithNonExistingCagtegory = mapper
				.writeValueAsString(aProduct().withNonExistingCategory().buildInput());
		String inputWithoutCategory = mapper.writeValueAsString(aProduct().withoutCategories().buildInput());
		return Stream.of(Arguments.of(validInput, EXISTING_ID.toString(), HttpStatus.OK),
				Arguments.of(JSON_PRODUCT_WITHOUT_NAME_PROPERTY, NON_EXISTING_ID.toString(), HttpStatus.BAD_REQUEST),
				Arguments.of(inputWithNegativePrice, INVALID_ID.toString(), HttpStatus.BAD_REQUEST),
				Arguments.of(inputWithNonExistingCagtegory, EXISTING_ID.toString(), HttpStatus.BAD_REQUEST),
				Arguments.of(inputWithoutCategory, EXISTING_ID.toString(), HttpStatus.BAD_REQUEST));
	}

	@ParameterizedTest
	@MethodSource("updateReturnSpecificHttpStatusArgumentSource")
	void update_ReturnSpecificStatus(String jsonBody, String id, HttpStatus status) throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);

		ResultActions result = mockMvc.perform(
				put("/v1/products/{productId}", id).header("Authorization", "Bearer " + accessToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(status.value()));
	}

	private static Stream<Arguments> deleteReturnSpecificHttpStatusArgumentSource() throws Exception {
		return Stream.of(Arguments.of(EXISTING_ID.toString(), HttpStatus.NO_CONTENT),
				Arguments.of(NON_EXISTING_ID.toString(), HttpStatus.NOT_FOUND),
				Arguments.of(INVALID_ID.toString(), HttpStatus.BAD_REQUEST));
	}

	@ParameterizedTest
	@MethodSource("deleteReturnSpecificHttpStatusArgumentSource")
	void delete_ReturnSpecificHttpStatus(String id, HttpStatus status) throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, ADMINSTRATOR_ACCOUNT);

		ResultActions result = mockMvc.perform(delete("/v1/products/{productId}", id)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(status.value()));
	}
}
