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
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
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
	private String existingEmail;
	private String nonExistingEmail;
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
		existingEmail = "alex@gmail.com";
		nonExistingEmail = "a1b2c3d4e5f6@gmail.com";
		validPassword = "123456";
		user = UserFactory.createUser();
		userInsert = UserFactory.createUserInsert();
		userUpdate = UserFactory.createUserUpdate();
		page = new PageImpl<>(List.of(user));
		changePasswordInput = new UserChangePasswordInput(validPassword);
		role = RoleFactory.createRole();

		// findAll
		when(repository.findAll(any(PageRequest.class))).thenReturn(page);

		// findById
		when(repository.findByIdWithRolesAndPrivileges(existingId)).thenReturn(Optional.of(user));
		when(repository.findByIdWithRolesAndPrivileges(nonExistingId)).thenReturn(Optional.empty());

		// findByEmail
		when(repository.findByEmail(existingEmail)).thenReturn(Optional.of(user));
		when(repository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());
		when(repository.findByEmailWithRolesAndPrivileges(existingEmail)).thenReturn(Optional.of(user));
		when(repository.findByEmailWithRolesAndPrivileges(nonExistingEmail)).thenReturn(Optional.empty());

		// insert
		when(repository.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("4546454");
		when(roleService.findById(any(UUID.class))).thenReturn(role);

		// update
		when(repository.getById(existingId)).thenReturn(user);
		doThrow(UserNotFoundException.class).when(repository).getById(nonExistingId);

		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		// mapper
		when(mapper.toUser(any(UserInsert.class))).thenReturn(user);
		when(mapper.toUser(any(UserUpdate.class))).thenReturn(user);
	}

	@Test
	public void findAllPaged_ReturnPage() {
		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<User> result = service.findAllPaged(pageRequest);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).findAll(pageRequest);
	}

	@Test
	public void findById_ReturnUserModel_IdExist() {

		User result = service.findById(existingId);

		assertNotNull(result);
		verify(repository, times(1)).findByIdWithRolesAndPrivileges(existingId);
	}

	@Test
	public void findById_ThrowUserNotFoundException_IdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

		verify(repository, times(1)).findByIdWithRolesAndPrivileges(nonExistingId);
	}

	@Test
	public void insert_ReturnUserModel_InputValid() {
		userInsert.setEmail(nonExistingEmail);

		User result = service.insert(userInsert);

		assertNotNull(result);
		verify(repository, times(1)).findByEmail(nonExistingEmail);
		verify(repository, times(1)).save(user);
	}

	@Test
	public void insert_ThrowsBusinessException_EmailAlreadyInUse() {
		userInsert.setEmail(existingEmail);

		assertThrows(BusinessException.class, () -> {
			service.insert(userInsert);
		});

		verify(repository, times(1)).findByEmail(existingEmail);
	}

	@Test
	public void update_ReturnUserModel_IdExist() {

		User result = service.update(existingId, userUpdate);

		assertNotNull(result);
		verify(repository, times(1)).findByIdWithRolesAndPrivileges(existingId);
		verify(repository, times(1)).save(user);
	}

	@Test
	public void update_ThrowUserNotFoundException_IdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
			service.update(nonExistingId, userUpdate);
		});

		verify(repository, times(1)).findByIdWithRolesAndPrivileges(nonExistingId);
	}

	@Test
	public void delete_DoNothing_IdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void delete_ThrowUserNotFoundException_IdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
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

	@Test
	public void changePassword_DoNothing_IdExists() {

		assertDoesNotThrow(() -> {
			service.changePassword(existingId, changePasswordInput);
		});

		verify(repository, times(1)).save(user);
	}

	@Test
	public void changePassword_ThrowUserNotFoundException_IdDoesNotExist() {

		assertThrows(UserNotFoundException.class, () -> {
			service.changePassword(nonExistingId, changePasswordInput);
		});

		verify(repository, times(1)).findByIdWithRolesAndPrivileges(nonExistingId);
	}

	@Test
	public void loadUserByUsername_ReturnUserDetails_IdExist() {

		UserDetails result = service.loadUserByUsername(existingEmail);

		assertNotNull(result);
		verify(repository, times(1)).findByEmailWithRolesAndPrivileges(existingEmail);
	}

	@Test
	public void loadUserByUsername_ThrowUsernameNotFoundException_EmailDoesNotExist() {

		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingEmail);
		});

		verify(repository, times(1)).findByEmailWithRolesAndPrivileges(nonExistingEmail);
	}
}
