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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.dto.RoleDTO;
import br.com.roanrobersson.rshop.domain.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.service.RoleService;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.factories.RoleFactory;

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
	private List<Role> roles;
	private RoleDTO roleDTO;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		dependentId = 3L;
		role = RoleFactory.createRole();
		roles = List.of(role);
		roleDTO = new RoleDTO();

		// findAllPaged
		when(repository.findAll(any(Sort.class))).thenReturn(roles);

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
		Sort sort = Sort.by(new Order(Direction.fromString("ASC"), "name"));

		List<Role> result = service.findAll(sort);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).findAll(sort);
	}

	@Test
	public void findById_ReturnRoleDTO_IdExist() {

		Role result = service.findById(existingId);

		assertNotNull(result);
	}

	@Test
	public void findById_ThrowResourceNotFoundException_IdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void insert_ReturnProductDTO() {

		Role result = service.insert(roleDTO);

		assertNotNull(result);
	}

	@Test
	public void update_ReturnRoleDTO_IdExist() {

		Role result = service.update(existingId, roleDTO);

		assertNotNull(result);
	}

	@Test
	public void update_ThrowResourceNotFoundException_IdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, roleDTO);
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
