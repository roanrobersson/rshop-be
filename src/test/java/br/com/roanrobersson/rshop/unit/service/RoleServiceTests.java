package br.com.roanrobersson.rshop.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.RoleNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;
import br.com.roanrobersson.rshop.domain.service.RoleService;
import br.com.roanrobersson.rshop.factory.PrivilegeFactory;
import br.com.roanrobersson.rshop.factory.RoleFactory;

@ExtendWith(SpringExtension.class)
public class RoleServiceTests {

	@InjectMocks
	private RoleService service;

	@Mock
	private RoleRepository repository;

	@Mock
	private PrivilegeService privilegeService;
	
	@Mock
	private RoleMapper mapper;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private Role role;
	private List<Role> roles;
	private RoleInputDTO roleInputDTO;
	private Privilege privilege;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		dependentId = 3L;
		role = RoleFactory.createRole();
		roles = List.of(role);
		roleInputDTO = RoleFactory.createRoleInputDTO();
		privilege = PrivilegeFactory.createPrivilege();

		// findAllPaged
		when(repository.findAll(any(Sort.class))).thenReturn(roles);

		// findByIdWithPrivileges
		when(repository.findByIdWithPrivileges(existingId)).thenReturn(Optional.of(role));
		when(repository.findByIdWithPrivileges(nonExistingId)).thenReturn(Optional.empty());

		// insert
		when(repository.save(any())).thenReturn(role);

		// update
		when(repository.getById(existingId)).thenReturn(role);
		doThrow(RoleNotFoundException.class).when(repository).getById(nonExistingId);

		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		// PrivilegeService
		when(privilegeService.findById(anyLong())).thenReturn(privilege);
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
	public void findById_ThrowRoleNotFoundException_IdDoesNotExist() {

		assertThrows(RoleNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void insert_ReturnRoleDTO() {

		Role result = service.insert(roleInputDTO);

		assertNotNull(result);
	}

	@Test
	public void update_ReturnRoleDTO_IdExist() {

		Role result = service.update(existingId, roleInputDTO);

		assertNotNull(result);
	}

	@Test
	public void update_ThrowRoleNotFoundException_IdDoesNotExist() {

		assertThrows(RoleNotFoundException.class, () -> {
			service.update(nonExistingId, roleInputDTO);
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
	public void delete_ThrowRoleNotFoundException_IdDoesNotExist() {

		assertThrows(RoleNotFoundException.class, () -> {
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
