package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.RoleBuilder.aRole;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.anExistingRole;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.aNonExistingRole;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import br.com.roanrobersson.rshop.builder.RoleBuilder;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.RoleNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;
import br.com.roanrobersson.rshop.domain.service.RoleService;

@ExtendWith(SpringExtension.class)
class RoleServiceTests {

	@InjectMocks
	private RoleService service;

	@Mock
	private RoleRepository repository;

	@Mock
	private PrivilegeService privilegeService;

	@Mock
	private RoleMapper mapper;

	private final UUID EXISTING_ID = UUID.fromString("7c4125cc-8116-4f11-8fc3-f40a0775aec7");
	private final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	private final UUID DEPENDENT_ID = UUID.fromString("821e3c67-7f22-46af-978c-b6269cb15387");

	@Test
	void findAllPaged_ReturnPage() {
		Sort sort = Sort.by(new Order(Direction.fromString("ASC"), "name"));
		List<Role> roles = List.of(anExistingRole().build());
		when(repository.findAll(sort)).thenReturn(roles);
		when(repository.findRolesWithPrivileges(roles)).thenReturn(roles);

		List<Role> result = service.findAll(sort);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(result, roles);
		verify(repository, times(1)).findAll(sort);
		verify(repository, times(1)).findRolesWithPrivileges(roles);
	}

	@Test
	void findById_ReturnRoleModel_IdExist() {
		Role role = anExistingRole().build();
		UUID id = role.getId();
		when(repository.findByIdWithPrivileges(id)).thenReturn(Optional.of(role));

		Role result = service.findById(id);

		assertNotNull(result);
		assertEquals(result, role);
		verify(repository, times(1)).findByIdWithPrivileges(id);
	}

	@Test
	void findById_ThrowRoleNotFoundException_IdDoesNotExist() {
		when(repository.findByIdWithPrivileges(NON_EXISTING_ID)).thenReturn(Optional.empty());

		assertThrows(RoleNotFoundException.class, () -> {
			service.findById(NON_EXISTING_ID);
		});

		verify(repository, times(1)).findByIdWithPrivileges(NON_EXISTING_ID);
	}

	@Test
	void insert_ReturnRoleModel_InputValid() {
		RoleBuilder builder = aNonExistingRole();
		RoleInput input = builder.buildInput();
		Role role = builder.build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toRole(input)).thenReturn(role);
		when(repository.save(role)).thenReturn(role);

		Role result = service.insert(input);

		assertNotNull(result);
		assertEquals(result, role);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toRole(input);
		verify(repository, times(1)).save(role);
	}

	@Test
	void insert_ThrowsBusinessException_NameAlreadyInUse() {
		RoleInput input = aNonExistingRole().withExistingName().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(aRole().build()));

		assertThrows(BusinessException.class, () -> {
			service.insert(input);
		});

		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void update_ReturnRoleModel_IdExist() {
		RoleBuilder builder = aNonExistingRole();
		RoleInput input = builder.buildInput();
		Role role = builder.withId(EXISTING_ID).build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithPrivileges(EXISTING_ID)).thenReturn(Optional.of(role));
		when(repository.save(role)).thenReturn(role);

		Role result = service.update(EXISTING_ID, input);

		assertNotNull(result);
		assertEquals(result, role);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithPrivileges(EXISTING_ID);
		verify(repository, times(1)).save(role);
	}

	@Test
	void update_ThrowRoleNotFoundException_IdDoesNotExist() {
		RoleInput input = aNonExistingRole().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithPrivileges(NON_EXISTING_ID)).thenReturn(Optional.empty());

		assertThrows(RoleNotFoundException.class, () -> {
			service.update(NON_EXISTING_ID, input);
		});

		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithPrivileges(NON_EXISTING_ID);
	}

	@Test
	void update_ThrowsBusinessException_NameAlreadyInUse() {
		RoleInput input = aNonExistingRole().withExistingName().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(aRole().build()));

		assertThrows(BusinessException.class, () -> {
			service.update(EXISTING_ID, input);
		});

		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void delete_DoNothingIdExists() {
		doNothing().when(repository).deleteById(EXISTING_ID);

		assertDoesNotThrow(() -> {
			service.delete(EXISTING_ID);
		});

		verify(repository, times(1)).deleteById(EXISTING_ID);
	}

	@Test
	void delete_ThrowRoleNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		assertThrows(RoleNotFoundException.class, () -> {
			service.delete(NON_EXISTING_ID);
		});

		verify(repository, times(1)).deleteById(NON_EXISTING_ID);
	}

	@Test
	void delete_ThrowDatabaseException_DependentId() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);

		assertThrows(DatabaseException.class, () -> {
			service.delete(DEPENDENT_ID);
		});

		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
