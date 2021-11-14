package br.com.roanrobersson.rshop.unit.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.dto.category.CategoryInsertDTO;
import br.com.roanrobersson.rshop.dto.category.CategoryResponseDTO;
import br.com.roanrobersson.rshop.dto.category.CategoryUpdateDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.factories.CategoryFactory;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;
import br.com.roanrobersson.rshop.services.CategoryService;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@InjectMocks
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private Category category;
	private PageImpl<Category> page;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		dependentId = 4L;
		
		category = CategoryFactory.createCategory();
		page = new PageImpl<>(List.of(category));
		
		// findAllPaged
		when(repository.findAll(any(PageRequest.class))).thenReturn(page);
		
		// findById
		when(repository.findById(existingId)).thenReturn(Optional.of(category));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		// insert
		when(repository.save(any())).thenReturn(category);
		
		// update
		when(repository.getById(existingId)).thenReturn(category);
		doThrow(EntityNotFoundException.class).when(repository).getById(nonExistingId);
		
		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void findAllPaged_ReturnPage() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<CategoryResponseDTO> result = service.findAllPaged(pageRequest);
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).findAll(pageRequest);
	}
	
	@Test
	public void findById_ReturnCategoryDTO_IdExist() {

		CategoryResponseDTO result = service.findById(existingId);
		
		assertNotNull(result);
	}
	
	@Test
	public void findById_ThrowResourceNotFoundException_IdDoesNotExist() {
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insert_ReturnProductDTO( ) {
		CategoryInsertDTO dto = new CategoryInsertDTO();
		
		CategoryResponseDTO result = service.insert(dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void update_ReturnCategoryDTO_IdExist() {
		CategoryUpdateDTO dto = new CategoryUpdateDTO();
		
		CategoryResponseDTO result = service.update(existingId, dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void update_ThrowResourceNotFoundException_IdDoesNotExist() {
		CategoryUpdateDTO dto = new CategoryUpdateDTO();
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, dto);
		});
	}
	
	@Test
	public void delete_DoNothingIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		verify(repository, times(1)).deleteById(existingId);
	}
	
	@Test
	public void delete_ThrowResourceNotFoundException_IdDoesNotExist() {
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		verify(repository, times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void delete_ThrowDatabaseException_DependentId() {
		
		assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		verify(repository, times(1)).deleteById(dependentId);
	}
}
