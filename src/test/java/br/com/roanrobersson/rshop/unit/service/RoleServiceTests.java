package br.com.roanrobersson.rshop.unit.service;

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
import java.util.UUID;

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

import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.RoleInput;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
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

	private UUID existingId;
	private UUID nonExistingId;
	private UUID dependentId;
	private String existingName;
	private String nonExistingName;
	private Role role;
	private List<Role> roles;
	private RoleInput roleInput;
	private Privilege privilege;

	@BeforeEach
	void setUp() throws Exception {
		existingId = UUID.fromString("18aace1e-f36a-4d71-b4d1-124387d9b63a");
		nonExistingId = UUID.fromString("00000000-0000-0000-0000-000000000000");
		dependentId = UUID.fromString("5e0b121c-9f12-4fd3-a7e6-179b5007149a");
		existingName = "ROLE_ADMIN";
		nonExistingName = "ROLE_INEXISTENT";
		role = RoleFactory.createRole();
		roles = List.of(role);
		roleInput = RoleFactory.createRoleInput();
		privilege = PrivilegeFactory.createPrivilege();

		// findAllPaged
		when(repository.findAll(any(Sort.class))).thenReturn(roles);

		// findByIdWithPrivileges
		when(repository.findByIdWithPrivileges(existingId)).thenReturn(Optional.of(role));
		when(repository.findByIdWithPrivileges(nonExistingId)).thenReturn(Optional.empty());

		// findByName
		when(repository.findByName(existingName)).thenReturn(Optional.of(role));
		when(repository.findByName(nonExistingName)).thenReturn(Optional.empty());

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
		when(privilegeService.findById(any(UUID.class))).thenReturn(privilege);

		// mapper
		when(mapper.toRole(any(RoleInput.class))).thenReturn(role);
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
	public void findById_ReturnRoleModel_IdExist() {

		Role result = service.findById(existingId);

		assertNotNull(result);
		verify(repository, times(1)).findByIdWithPrivileges(existingId);
	}

	@Test
	public void findById_ThrowRoleNotFoundException_IdDoesNotExist() {

		assertThrows(RoleNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

		verify(repository, times(1)).findByIdWithPrivileges(nonExistingId);
	}

	@Test
	public void insert_ReturnRoleModel_InputValid() {
		roleInput.setName(nonExistingName);

		Role result = service.insert(roleInput);

		assertNotNull(result);
		verify(repository, times(1)).findByName(nonExistingName);
		verify(repository, times(1)).save(role);
	}

	@Test
	public void insert_ThrowsBusinessException_NameAlreadyInUse() {
		roleInput.setName(existingName);

		assertThrows(BusinessException.class, () -> {
			service.insert(roleInput);
		});

		verify(repository, times(1)).findByName(existingName);
	}

	@Test
	public void update_ReturnRoleModel_IdExist() {
		roleInput.setName(nonExistingName);

		Role result = service.update(existingId, roleInput);

		assertNotNull(result);
		verify(repository, times(1)).findByName(nonExistingName);
		verify(repository, times(1)).findByIdWithPrivileges(existingId);
		verify(repository, times(1)).save(role);
	}

	@Test
	public void update_ThrowRoleNotFoundException_IdDoesNotExist() {
		roleInput.setName(nonExistingName);

		assertThrows(RoleNotFoundException.class, () -> {
			service.update(nonExistingId, roleInput);
		});

		verify(repository, times(1)).findByName(nonExistingName);
		verify(repository, times(1)).findByIdWithPrivileges(nonExistingId);
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
