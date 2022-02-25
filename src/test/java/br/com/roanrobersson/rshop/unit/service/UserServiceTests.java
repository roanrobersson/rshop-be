package br.com.roanrobersson.rshop.unit.service;

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
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.UserNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;
import br.com.roanrobersson.rshop.domain.service.AuthService;
import br.com.roanrobersson.rshop.domain.service.RoleService;
import br.com.roanrobersson.rshop.domain.service.UserService;
import br.com.roanrobersson.rshop.factory.RoleFactory;
import br.com.roanrobersson.rshop.factory.UserFactory;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

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

	private UUID existingId;
	private UUID nonExistingId;
	private UUID dependentId;
	private String existingUserName;
	private String nonExistingUserName;
	private String validPassword;
	private User user;
	private UserInsert userInsert;
	private UserUpdate userUpdate;
	private PageImpl<User> page;
	private UserChangePasswordInput changePasswordInput;
	private Role role;

	@BeforeEach
	void setUp() throws Exception {
		existingId = UUID.fromString("821e3c67-7f22-46af-978c-b6269cb15387");
		nonExistingId = UUID.fromString("00000000-0000-0000-0000-000000000000");
		dependentId = UUID.fromString("8903af19-36e2-44d9-b649-c3319f33be20");
		existingUserName = "alex@gmail.com";
		nonExistingUserName = "a1b2c3d4e5f6@gmail.com";
		validPassword = "123456";
		user = UserFactory.createUser();
		userInsert = UserFactory.createUserInsert();
		userUpdate = UserFactory.createUserUpdate();
		page = new PageImpl<>(List.of(user));
		changePasswordInput = new UserChangePasswordInput(validPassword);
		role = RoleFactory.createRole();

		// findAllPaged
		when(repository.findAll(any(PageRequest.class))).thenReturn(page);

		// findById
		when(repository.findByIdWithRolesAndPrivileges(existingId)).thenReturn(Optional.of(user));
		when(repository.findByIdWithRolesAndPrivileges(nonExistingId)).thenReturn(Optional.empty());

		// findByUsername
		when(repository.findByEmailWithRolesAndPrivileges(existingUserName)).thenReturn(Optional.of(user));
		
		// insert
		when(repository.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("4546454");
		when(roleService.findById(any(UUID.class))).thenReturn(role);
		when(mapper.toUser(userInsert)).thenReturn(user);
		
		// update
		when(repository.getById(existingId)).thenReturn(user);
		doThrow(UserNotFoundException.class).when(repository).getById(nonExistingId);

		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		// loadUserByUsername
		when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
		doThrow(UsernameNotFoundException.class).when(repository).findByEmail(nonExistingUserName);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<User> result = service.findAllPaged(pageRequest);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).findAll(pageRequest);
	}

	@Test
	public void findByIdShouldReturnUserModelWhenIdExist() {

		User result = service.findById(existingId);

		assertNotNull(result);
	}

	@Test
	public void findByIdShouldThrowUserNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void insertShouldReturnUserModel() {

		User result = service.insert(userInsert);

		assertNotNull(result);
	}

	@Test
	public void updateShouldReturnUserModelWhenIdExist() {

		User result = service.update(existingId, userUpdate);

		assertNotNull(result);
	}

	@Test
	public void updateShouldThrowUserNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
			service.update(nonExistingId, userUpdate);
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
	public void deleteShouldThrowUserNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
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

	@Test
	public void changePasswordShouldDoNothingWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.changePassword(existingId, changePasswordInput);
		});

		verify(repository, times(1)).save(user);
	}

	@Test
	public void changePasswordShouldThrowUserNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
			service.changePassword(nonExistingId, changePasswordInput);
		});

		verify(repository, times(1)).findByIdWithRolesAndPrivileges(nonExistingId);
	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenIdExist() {

		UserDetails result = service.loadUserByUsername(existingUserName);

		assertNotNull(result);
	}

	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenEmailDoesNotExist() {

		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUserName);
		});

		verify(repository, times(1)).findByEmailWithRolesAndPrivileges(nonExistingUserName);
	}
}
