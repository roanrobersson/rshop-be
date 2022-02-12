package br.com.roanrobersson.rshop.integration.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.factory.ProductFactory;
import br.com.roanrobersson.rshop.factory.TokenUtil;

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
	
	private UUID existingId;
	private UUID nonExistingId;
	private Long countTotalProducts;
	private String username;
	private String password;
	private ProductInputDTO productInputDTO;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = UUID.fromString("7c4125cc-8116-4f11-8fc3-f40a0775aec7");
		nonExistingId = UUID.fromString("00000000-0000-0000-0000-000000000000");
		countTotalProducts = 25L;
		username = "administrador@gmail.com";
		password = "12345678";
		productInputDTO = ProductFactory.createProductInputDTO();
		productDTO = ProductFactory.createProductDTO();
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/v1/products?page=0&size=12&sort=name,asc")
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
		result.andExpect(jsonPath("$.content").exists());		
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("O Senhor dos Anéis"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer"));		
	}
	
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		String inputJsonBody = objectMapper.writeValueAsString(productInputDTO);
		String expectedJsonBody = objectMapper.writeValueAsString(productDTO); 
		
		ResultActions result = 
				mockMvc.perform(put("/v1/products/{id}", existingId)
					.header("Authorization", "Bearer " + accessToken)
					.content(inputJsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(content().json(expectedJsonBody));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		String jsonBody = objectMapper.writeValueAsString(productInputDTO);
		
		ResultActions result = 
				mockMvc.perform(put("/v1/products/{id}", nonExistingId)
					.header("Authorization", "Bearer " + accessToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
}
