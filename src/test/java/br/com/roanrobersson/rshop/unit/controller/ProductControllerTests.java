package br.com.roanrobersson.rshop.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.ProductService;
import br.com.roanrobersson.rshop.factory.ProductFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

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
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private Product product;
	private ProductInputDTO productInputDTO;
	private ProductDTO productDTO;
	private PageImpl<Product> page;
	private String username;
	private String password;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = ProductFactory.createProduct();
		productInputDTO = ProductFactory.createProductInputDTO();
		productDTO = ProductFactory.createProductDTO();
		page = new PageImpl<>(List.of(product));
		username = "administrador@gmail.com";
		password = "12345678";
		
		// findAll
		when(service.findAllPaged(any(), anyString(), any())).thenReturn(page);
		
		// findById
		when(service.findById(existingId)).thenReturn(product);
		when(service.findById(nonExistingId)).thenThrow(ProductNotFoundException.class);
	
		// insert
		when(service.insert(any())).thenReturn(product);
		
		// update
		when(service.update(eq(existingId), any())).thenReturn(product);
		when(service.update(eq(nonExistingId), any())).thenThrow(ProductNotFoundException.class);

		// delete
		doNothing().when(service).delete(existingId);
		doThrow(ProductNotFoundException.class).when(service).delete(nonExistingId);
		doThrow(DatabaseException.class).when(service).delete(dependentId);
	}
	
	@Test
	public void findAll_ReturnPage() throws Exception {
		
		ResultActions result =
			mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
	}
	
	@Test
	public void findById_ReturnProduct_IdExist() throws Exception {
		
		ResultActions result =
			mockMvc.perform(get("/products/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.id").value(existingId));
	}
	
	@Test
	public void findById_ReturnNotFound_IdDoesNotExist() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingId)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insert_ReturnProductDTO_ValidProductInputDTO() throws Exception {
		String accessToken = obtainAccessToken(username, password);
		String inputJsonBody = objectMapper.writeValueAsString(productInputDTO);
		String expectedJsonBody = objectMapper.writeValueAsString(productDTO); 
				
		ResultActions result =
				mockMvc.perform(post("/products")
					.header("Authorization", "Bearer " + accessToken)
					.content(inputJsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isCreated());
		result.andExpect(content().json(expectedJsonBody));
	}
	
	@Test
	public void insert_ReturnBadRequest_NoPositivePrice() throws Exception {
		String accessToken = obtainAccessToken(username, password);
		ProductInputDTO invalidProductInputDTO = ProductFactory.createProductInputDTO();
		invalidProductInputDTO.setPrice(BigDecimal.valueOf(0));
		String jsonBody = objectMapper.writeValueAsString(invalidProductInputDTO);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.header("Authorization", "Bearer " + accessToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void update_ReturnProductDTO_IdExists() throws Exception {
		String accessToken = obtainAccessToken(username, password);
		String jsonBody = objectMapper.writeValueAsString(productInputDTO);
		String expectedJsonBody = objectMapper.writeValueAsString(productDTO); 
				
		ResultActions result =
				mockMvc.perform(put("/products/{id}", existingId)
					.header("Authorization", "Bearer " + accessToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isOk());
		result.andExpect(content().json(expectedJsonBody));
	}
	
	@Test
	public void update_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = obtainAccessToken(username, password);
		String jsonBody = objectMapper.writeValueAsString(productInputDTO);
		
		ResultActions result =
				mockMvc.perform(put("/products/{id}", nonExistingId)
					.header("Authorization", "Bearer " + accessToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void delete_ReturnNoContent_IdExist() throws Exception {
		String accessToken = obtainAccessToken(username, password);
		
		ResultActions result =
			mockMvc.perform(delete("/products/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));
				
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void delete_ReturnNotFound_IdDoesNotExist() throws Exception {
		String accessToken = obtainAccessToken(username, password);
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", nonExistingId)
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isNotFound());
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", clientId);
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result 
	    	= mockMvc.perform(post("/oauth/token")
	    		.params(params)
	    		.with(httpBasic(clientId, clientSecret))
	    		.accept("application/json;charset=UTF-8"))
	        	.andExpect(status().isOk())
	        	.andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}	
}