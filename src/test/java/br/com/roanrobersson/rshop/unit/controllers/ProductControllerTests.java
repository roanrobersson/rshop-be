package br.com.roanrobersson.rshop.unit.controllers;

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

import br.com.roanrobersson.rshop.dto.product.ProductInsertDTO;
import br.com.roanrobersson.rshop.dto.product.ProductResponseDTO;
import br.com.roanrobersson.rshop.dto.product.ProductUpdateDTO;
import br.com.roanrobersson.rshop.factories.ProductFactory;
import br.com.roanrobersson.rshop.services.ProductService;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

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
	private ProductInsertDTO productInsertDTO;
	private ProductResponseDTO existingProductResponseDTO;
	private PageImpl<ProductResponseDTO> page;
	private String username;
	private String password;
	
	@BeforeEach
	void setUp() throws Exception {
			
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		productInsertDTO = ProductFactory.createProductInsertDTO();
		existingProductResponseDTO = ProductFactory.createProductResponseDTO(existingId);
		
		page = new PageImpl<>(List.of(existingProductResponseDTO));
		
		username = "alex@gmail.com";
		password = "123456";
		
		// findAll
		when(service.findAllPaged(any(), anyString(), any())).thenReturn(page);
		
		// findById
		when(service.findById(existingId)).thenReturn(existingProductResponseDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
	
		// insert
		when(service.insert(any())).thenReturn(existingProductResponseDTO);
		
		// update
		when(service.update(eq(existingId), any())).thenReturn(existingProductResponseDTO);
		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

		// delete
		doNothing().when(service).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
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
	public void insert_ReturnProductResponseDTO_ValidProductInsertDTO() throws Exception{
		String accessToken = obtainAccessToken(username, password);
		String jsonBody = objectMapper.writeValueAsString(productInsertDTO);
		ProductResponseDTO expectedProductResponseDTO = ProductFactory.createProductResponseDTO(existingId);
		String expectedJsonBody = objectMapper.writeValueAsString(expectedProductResponseDTO); 
				
		ResultActions result =
				mockMvc.perform(post("/products")
					.header("Authorization", "Bearer " + accessToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isCreated());
		result.andExpect(content().json(expectedJsonBody));
	}
	
	@Test
	public void insert_ReturnUnprocessableEntity_NoPositivePrice() throws Exception{
		String accessToken = obtainAccessToken(username, password);
		ProductInsertDTO invalidProductInsertDTO = ProductFactory.createProductInsertDTO();
		invalidProductInsertDTO.setPrice(0.0);
		String jsonBody = objectMapper.writeValueAsString(invalidProductInsertDTO);
		
		ResultActions result =
				mockMvc.perform(post("/products")
					.header("Authorization", "Bearer " + accessToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
					
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void update_ReturnProductResponseDTO_IdExists() throws Exception{
		String accessToken = obtainAccessToken(username, password);
		String jsonBody = objectMapper.writeValueAsString(productInsertDTO);
		ProductUpdateDTO expectedProductDTO = ProductFactory.createProductUpdateDTO(1L);
		String expectedJsonBody = objectMapper.writeValueAsString(expectedProductDTO); 
				
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
	public void update_ReturnNotFound_IdDoesNotExist() throws Exception{
		String accessToken = obtainAccessToken(username, password);
		String jsonBody = objectMapper.writeValueAsString(productInsertDTO);
		
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