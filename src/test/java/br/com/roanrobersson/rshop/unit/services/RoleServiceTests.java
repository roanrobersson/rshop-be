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
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.dto.RoleDTO;
import br.com.roanrobersson.rshop.dto.response.RoleResponseDTO;
import br.com.roanrobersson.rshop.dto.role.RoleUpdateDTO;
import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.factories.RoleFactory;
import br.com.roanrobersson.rshop.repositories.RoleRepository;
import br.com.roanrobersson.rshop.services.RoleService;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class RoleServiceTests {

	@InjectMocks
	private RoleService service;
	
	@Mock
	private RoleRepository repository;
	
	@Mock
	private ModelMapper modelMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private Role role;
	private PageImpl<Role> page;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		dependentId = 3L;
		
		role = RoleFactory.createRole();
		page = new PageImpl<>(List.of(role));
		
		// findAllPaged
		when(repository.findAll(any(PageRequest.class))).thenReturn(page);
		
		// findById
		when(repository.findById(existingId)).thenReturn(Optional.of(role));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		// insert
		when(repository.save(any())).thenReturn(role);
		
		// update
		when(repository.getById(existingId)).thenReturn(role);
		doThrow(EntityNotFoundException.class).when(repository).getById(nonExistingId);
		
		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		// modelMapper
		when(modelMapper.map(any(), any())).thenReturn(role);
	}
	
	@Test
	public void findAllPaged_ReturnPage() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<RoleResponseDTO> result = service.findAllPaged(pageRequest);
		
		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).findAll(pageRequest);
	}
	
	@Test
	public void findById_ReturnRoleDTO_IdExist() {

		RoleResponseDTO result = service.findById(existingId);
		
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
		RoleDTO dto = new RoleDTO();
		
		RoleResponseDTO result = service.insert(dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void update_ReturnRoleDTO_IdExist() {
		RoleUpdateDTO dto = new RoleUpdateDTO();
		
		RoleResponseDTO result = service.update(existingId, dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void update_ThrowResourceNotFoundException_IdDoesNotExist() {
		RoleUpdateDTO dto = new RoleUpdateDTO();
		
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
