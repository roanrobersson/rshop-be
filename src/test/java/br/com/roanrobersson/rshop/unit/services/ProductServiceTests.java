package br.com.roanrobersson.rshop.unit.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.product.ProductUpdateDTO;
import br.com.roanrobersson.rshop.api.v1.dto.response.ProductResponseDTO;
import br.com.roanrobersson.rshop.domain.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.ProductService;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.factories.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private ModelMapper modelMapper;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private Product product;
	private PageImpl<Product> page;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		dependentId = 4L;
		product = ProductFactory.createProduct();
		page = new PageImpl<>(List.of(product));
		
		// findAllPaged
		when(repository.search(any(), anyString(), any()))
		.thenReturn(page);
		
		// findById
		when(repository.findById(existingId)).thenReturn(Optional.of(product));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		// insert
		when(repository.save(any())).thenReturn(product);
		
		// update
		when(repository.getById(existingId)).thenReturn(product);
		doThrow(EntityNotFoundException.class).when(repository).getById(nonExistingId);
		
		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		// modelMapper
		when(modelMapper.map(any(), any())).thenReturn(product);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		Long categoryId = 0L;
		String name = "";
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductResponseDTO> result = service.findAllPaged(categoryId, name, pageRequest);
		assertNotNull(result);
		
		assertFalse(result.isEmpty());
		verify(repository, times(1)).search(null, name, pageRequest);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExist() {

		ProductResponseDTO result = service.findById(existingId);
		
		assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insertShouldReturnProductDTO( ) {
		ProductDTO dto = new ProductDTO();
		
		ProductResponseDTO result = service.insert(dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExist() {
		ProductUpdateDTO dto = new ProductUpdateDTO();
		
		ProductResponseDTO result = service.update(existingId, dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void updateShouldShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		ProductUpdateDTO dto = new ProductUpdateDTO();
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, dto);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		verify(repository, times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		verify(repository, times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		verify(repository, times(1)).deleteById(dependentId);
	}
}
