package br.com.roanrobersson.rshop.unit.services;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.dto.input.UserChangePasswordInputDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.domain.Role;
import br.com.roanrobersson.rshop.domain.User;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;
import br.com.roanrobersson.rshop.domain.service.AuthService;
import br.com.roanrobersson.rshop.domain.service.UserService;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.factories.RoleFactory;
import br.com.roanrobersson.rshop.factories.UserFactory;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private AuthService authService;

	@Mock
	private ModelMapper modelMapper;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private String existingUserName;
	private String nonExistingUserName;
	private String validPassword;
	private User user;
	private UserInsertDTO userInsertDTO;
	private UserUpdateDTO userUpdateDTO;
	private PageImpl<User> page;
	private UserChangePasswordInputDTO changePasswordDTO;
	private Role role;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = Long.MAX_VALUE;
		dependentId = 4L;
		existingUserName = "alex@gmail.com";
		nonExistingUserName = "a1b2c3d4e5f6@gmail.com";
		validPassword = "123456";
		user = UserFactory.createUser();
		userInsertDTO = UserFactory.createUserInsertDTO();
		userUpdateDTO = UserFactory.createUserUpdateDTO();
		page = new PageImpl<>(List.of(user));
		changePasswordDTO = new UserChangePasswordInputDTO(validPassword);
		role = RoleFactory.createRole();

		// findAllPaged
		when(repository.findAll(any(PageRequest.class))).thenReturn(page);

		// findById
		when(repository.findById(existingId)).thenReturn(Optional.of(user));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// insert
		when(repository.save(any())).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("4546454");
		when(roleRepository.findById(any())).thenReturn(Optional.of(role));

		// update
		when(repository.getById(existingId)).thenReturn(user);
		doThrow(EntityNotFoundException.class).when(repository).getById(nonExistingId);

		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		// loadUserByUsername
		when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
		doThrow(UsernameNotFoundException.class).when(repository).findByEmail(nonExistingUserName);

		// modelMapper
		when(modelMapper.map(any(), any())).thenReturn(user);
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
	public void findByIdShouldReturnUserDTOWhenIdExist() {

		User result = service.findById(existingId);

		assertNotNull(result);
	}

	@Test
	public void findByIdShouldShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void insertShouldReturnUserResponseDTO() {

		User result = service.insert(userInsertDTO);

		assertNotNull(result);
	}

	@Test
	public void updateShouldReturnUserDTOWhenIdExist() {

		User result = service.update(existingId, userUpdateDTO);

		assertNotNull(result);
	}

	@Test
	public void updateShouldShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, userUpdateDTO);
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
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
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
			service.changePassword(existingId, changePasswordDTO);
		});

		verify(repository, times(1)).save(user);
	}

	@Test
	public void changePasswordShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.changePassword(nonExistingId, changePasswordDTO);
		});

		verify(repository, times(1)).findById(nonExistingId);
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

		verify(repository, times(1)).findByEmail(nonExistingUserName);
	}
}
