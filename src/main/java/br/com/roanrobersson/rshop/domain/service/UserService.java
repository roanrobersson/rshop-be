package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.event.OnRegistrationCompleteEvent;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UserNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static final String MSG_USER_IN_USE = "User with ID %s cannot be removed, because it is in use";
	private static final String MSG_USER_WITH_EMAIL_NOT_FOUND = "User with email %s not found";
	private static final String MSG_USER_ALREADY_EXISTS = "There is already a user registered with that email %s";

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserMapper mapper;

	private UUID defaultUserRoleId = UUID.fromString("18aace1e-f36a-4d71-b4d1-124387d9b63a");

	@Transactional(readOnly = true)
	public Page<User> findAllPaged(PageRequest pageRequest) {
		Page<User> users = repository.findAll(pageRequest);
		repository.findWithRolesAndPrivileges(users.toList());
		return users;
	}

	@Transactional(readOnly = true)
	public User findById(UUID userId) {
		return repository.findByIdWithRolesAndPrivileges(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return repository.findByEmailWithRolesAndPrivileges(email)
				.orElseThrow(() -> new UserNotFoundException(String.format(MSG_USER_WITH_EMAIL_NOT_FOUND, email)));
	}

	@Transactional
	public User insert(UserInsert userInsert) {
		validateUniqueInsert(userInsert);
		User user = mapper.toUser(userInsert);
		user.setPassword(passwordEncoder.encode(userInsert.getPassword()));
		Role defaultRole = roleService.findById(defaultUserRoleId);
		user.getRoles().add(defaultRole);
		repository.save(user);
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
		return user;
	}

	@Transactional
	public User update(UUID userId, UserUpdate userUpdate) {
		User user = findById(userId);
		mapper.update(userUpdate, user);
		return repository.save(user);
	}

	public void delete(UUID userId) {
		try {
			repository.deleteById(userId);
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_USER_IN_USE, userId));
		}
	}

	@Transactional
	public void changePassword(UUID userId, UserChangePasswordInput userChangePasswordInput) {
		User user = findById(userId);
		user.setPassword(passwordEncoder.encode(userChangePasswordInput.getNewPassword()));
		repository.save(user);
	}

	@Transactional
	public Set<Role> getRoles(UUID userId) {
		User user = findById(userId);
		return user.getRoles();
	}

	@Transactional
	public void grantRole(UUID userId, UUID roleId) {
		User user = findById(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().add(role);
		repository.save(user);
	}

	@Transactional
	public void revokeRole(UUID userId, UUID roleId) {
		User user = findById(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().remove(role);
		repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optional = repository.findByEmailWithRolesAndPrivileges(email);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("Username not found");
		}
		return optional.get();
	}

	private void validateUniqueInsert(UserInsert userInsert) {
		Optional<User> optional = repository.findByEmail(userInsert.getEmail());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_USER_ALREADY_EXISTS, userInsert.getName()));
		}
	}
}
