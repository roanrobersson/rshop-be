package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.UserBuilder.DEPENDENT_ID;
import static br.com.roanrobersson.rshop.builder.UserBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.UserBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.UserBuilder.aNonExistingUser;
import static br.com.roanrobersson.rshop.builder.UserBuilder.anExistingUser;
import static br.com.roanrobersson.rshop.builder.UserBuilder.anUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.builder.UserBuilder;
import br.com.roanrobersson.rshop.domain.dto.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.domain.dto.input.UserInsert;
import br.com.roanrobersson.rshop.domain.dto.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.exception.UserNotFoundException;
import br.com.roanrobersson.rshop.domain.mapper.UserMapper;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;
import br.com.roanrobersson.rshop.domain.service.AuthService;
import br.com.roanrobersson.rshop.domain.service.RoleService;
import br.com.roanrobersson.rshop.domain.service.UserService;

@ExtendWith(SpringExtension.class)
class UserServiceUT {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
	private RoleService roleService;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private AuthService authService;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@Mock
	private UserMapper mapper;

	@Test
	void findAllPaged_ReturnUserPage() {
		User user = anExistingUser().build();
		List<User> userList = List.of(user);
		Pageable pageable = PageRequest.of(0, 10);
		Page<User> page = new PageImpl<>(List.of(anExistingUser().build()));
		when(repository.findAll(pageable)).thenReturn(page);
		when(repository.findWithRoles(userList)).thenReturn(userList);

		Page<User> actual = service.list(pageable);

		assertThat(actual.getContent()).containsExactlyInAnyOrder(user).usingRecursiveFieldByFieldElementComparator();
		verify(repository, times(1)).findAll(pageable);
		verify(repository, times(1)).findWithRoles(userList);
	}

	@Test
	void findById_ReturnUser_IdExist() {
		User user = anExistingUser().build();
		when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(user));

		User actual = service.findById(EXISTING_ID);

		assertThat(actual).usingRecursiveComparison().isEqualTo(user);
		verify(repository, times(1)).findById(EXISTING_ID);
	}

	@Test
	void findById_ThrowUserNotFoundException_IdDoesNotExist() {
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.findById(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(UserNotFoundException.class);
		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}

	@Test
	void insert_ReturnUser_InputValid() {
		UserBuilder builder = aNonExistingUser();
		UserInsert insert = builder.buildInsert();
		User user = builder.build();
		String encodedPassword = "00000000";
		when(repository.findByEmail(insert.getEmail())).thenReturn(Optional.empty());
		when(mapper.toUser(insert)).thenReturn(user);
		when(passwordEncoder.encode(insert.getPassword())).thenReturn(encodedPassword);
		when(repository.save(user)).thenReturn(user);

		User result = service.insert(insert);

		assertNotNull(result);
		assertEquals(result, user);
		verify(repository, times(1)).findByEmail(insert.getEmail());
		verify(mapper, times(1)).toUser(insert);
		verify(passwordEncoder, times(1)).encode(insert.getPassword());
		verify(repository, times(1)).save(user);
	}

	@Test
	void insert_ThrowsUniqueException_EmailAlreadyInUse() {
		UserInsert insert = aNonExistingUser().buildInsert();
		when(repository.findByEmail(insert.getEmail())).thenReturn(Optional.of(anUser().build()));

		Throwable thrown = catchThrowable(() -> {
			service.insert(insert);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
		verify(repository, times(1)).findByEmail(insert.getEmail());
	}

	@Test
	void update_ReturnUser_IdExist() {
		UserBuilder builder = aNonExistingUser().withId(EXISTING_ID);
		UserUpdate update = builder.buildUpdate();
		User user = builder.build();
		when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(user));
		when(repository.save(user)).thenReturn(user);

		User result = service.update(EXISTING_ID, update);

		assertNotNull(result);
		assertEquals(result, user);
		verify(repository, times(1)).findById(EXISTING_ID);
		verify(mapper, times(1)).update(update, user);
		verify(repository, times(1)).save(user);
	}

	@Test
	void update_ThrowUserNotFoundException_IdDoesNotExist() {
		UserUpdate update = anExistingUser().buildUpdate();
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.update(NON_EXISTING_ID, update);
		});

		assertThat(thrown).isExactlyInstanceOf(UserNotFoundException.class);
		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}

	@Test
	void delete_DoNothing_IdExists() {
		doNothing().when(repository).deleteById(EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(EXISTING_ID);
		});

		assertThat(thrown).isNull();
		verify(repository, times(1)).deleteById(EXISTING_ID);
	}

	@Test
	void delete_ThrowUserNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(UserNotFoundException.class);
		verify(repository, times(1)).deleteById(NON_EXISTING_ID);
	}

	@Test
	void delete_ThrowEntityInUseException_DependentId() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(DEPENDENT_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(EntityInUseException.class);
		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}

	@Test
	void changePassword_DoNothing_IdExists() {
		UserChangePasswordInput passwordInput = new UserChangePasswordInput("a3g&3Pd#");
		User user = anUser().build();
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.of(user));
		when(repository.save(user)).thenReturn(user);

		Throwable thrown = catchThrowable(() -> {
			service.changePassword(NON_EXISTING_ID, passwordInput);
		});

		assertThat(thrown).isNull();
		verify(repository, times(1)).findById(NON_EXISTING_ID);
		verify(repository, times(1)).save(user);
	}

	@Test
	void changePassword_ThrowUserNotFoundException_IdDoesNotExist() {
		UserChangePasswordInput passwordInput = new UserChangePasswordInput("a3g&3Pd#");
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.changePassword(NON_EXISTING_ID, passwordInput);
		});

		assertThat(thrown).isExactlyInstanceOf(UserNotFoundException.class);
		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}
}
