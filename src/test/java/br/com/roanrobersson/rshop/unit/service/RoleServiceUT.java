package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.RoleBuilder.aNonExistingRole;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.aRole;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.anExistingRole;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.builder.RoleBuilder;
import br.com.roanrobersson.rshop.domain.dto.input.RoleInput;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.RoleNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;
import br.com.roanrobersson.rshop.domain.service.RoleService;

@ExtendWith(SpringExtension.class)
class RoleServiceUT {

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
	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by(Order.asc("id")));

	@Test
	void findAllPaged_ReturnRolePage() {
		Role role = anExistingRole().build();
		List<Role> roleList = List.of(role);
		Page<Role> roles = new PageImpl<Role>(roleList);
		when(repository.findAll(DEFAULT_PAGEABLE)).thenReturn(roles);
		when(repository.findRolesWithPrivileges(roleList)).thenReturn(roleList);

		Page<Role> actual = service.list(DEFAULT_PAGEABLE);

		assertThat(actual.getContent()).containsExactlyInAnyOrder(role).usingRecursiveFieldByFieldElementComparator();
		verify(repository, times(1)).findAll(DEFAULT_PAGEABLE);
		verify(repository, times(1)).findRolesWithPrivileges(roleList);
	}

	@Test
	void findById_ReturnRole_IdExist() {
		Role role = anExistingRole().build();
		UUID id = role.getId();
		when(repository.findByIdWithPrivileges(id)).thenReturn(Optional.of(role));

		Role actual = service.findById(id);

		assertThat(actual).usingRecursiveComparison().isEqualTo(role);
		verify(repository, times(1)).findByIdWithPrivileges(id);
	}

	@Test
	void findById_ThrowsRoleNotFoundException_IdDoesNotExist() {
		when(repository.findByIdWithPrivileges(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.findById(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(RoleNotFoundException.class);
		verify(repository, times(1)).findByIdWithPrivileges(NON_EXISTING_ID);
	}

	@Test
	void insert_ReturnRole_InputValid() {
		RoleBuilder builder = aNonExistingRole();
		RoleInput input = builder.buildInput();
		Role role = builder.build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toRole(input)).thenReturn(role);
		when(repository.save(role)).thenReturn(role);

		Role actual = service.insert(input);

		assertThat(actual).usingRecursiveComparison().isEqualTo(role);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toRole(input);
		verify(repository, times(1)).save(role);
	}

	@Test
	void insert_ThrowsUniqueException_NameAlreadyInUse() {
		RoleInput input = aNonExistingRole().withExistingName().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(aRole().build()));

		Throwable thrown = catchThrowable(() -> {
			service.insert(input);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void update_ReturnRole_IdExist() {
		RoleBuilder builder = aNonExistingRole();
		RoleInput input = builder.buildInput();
		Role role = builder.withId(EXISTING_ID).build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithPrivileges(EXISTING_ID)).thenReturn(Optional.of(role));
		when(repository.save(role)).thenReturn(role);

		Role actual = service.update(EXISTING_ID, input);

		assertThat(actual).usingRecursiveComparison().isEqualTo(role);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithPrivileges(EXISTING_ID);
		verify(repository, times(1)).save(role);
	}

	@Test
	void update_ThrowsRoleNotFoundException_IdDoesNotExist() {
		RoleInput input = aNonExistingRole().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithPrivileges(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.update(NON_EXISTING_ID, input);
		});

		assertThat(thrown).isExactlyInstanceOf(RoleNotFoundException.class);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithPrivileges(NON_EXISTING_ID);
	}

	@Test
	void update_ThrowsUniqueException_NameAlreadyInUse() {
		RoleInput input = aNonExistingRole().withExistingName().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(aRole().build()));

		Throwable thrown = catchThrowable(() -> {
			service.update(EXISTING_ID, input);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void delete_DoNothingIdExists() {
		doNothing().when(repository).deleteById(EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(EXISTING_ID);
		});

		assertThat(thrown).isNull();
		verify(repository, times(1)).deleteById(EXISTING_ID);
	}

	@Test
	void delete_ThrowsRoleNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(RoleNotFoundException.class);
		verify(repository, times(1)).deleteById(NON_EXISTING_ID);
	}

	@Test
	void delete_ThrowsEntityInUseException_DependentId() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(DEPENDENT_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(EntityInUseException.class);
		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
