package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.roanrobersson.rshop.api.v1.dto.input.UserChangePasswordInputDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UserNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static final String MSG_USER_IN_USE = "Role with ID %d cannot be removed, because it is in use";
	private static final String MSG_USER_WITH_EMAIL_NOT_FOUND = "User with email %d not found";

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserMapper mapper;

	private Long defaultUserRoleId = 1L;

	@Transactional(readOnly = true)
	public Page<User> findAllPaged(PageRequest pageRequest) {
		Page<User> users = repository.findAll(pageRequest);
		repository.findWithRolesAndPrivileges(users.toList());
		return users;
	}

	@Transactional(readOnly = true)
	public User findById(Long userId) {
		return repository.findByIdWithRolesAndPrivileges(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return repository.findByEmailWithRolesAndPrivileges(email)
				.orElseThrow(() -> new UserNotFoundException(String.format(MSG_USER_WITH_EMAIL_NOT_FOUND, email)));
	}

	@Transactional
	public User insert(UserInsertDTO userInsertDTO) {
		User user = mapper.toUser(userInsertDTO);
		user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
		Role defaultRole = roleService.findById(defaultUserRoleId);
		user.getRoles().add(defaultRole);
		return repository.save(user);
	}

	@Transactional
	public User update(Long userId, UserUpdateDTO userUpdateDTO) {
		User user = findById(userId);
		mapper.update(userUpdateDTO, user);
		return repository.save(user);
	}

	public void delete(Long userId) {
		try {
			repository.deleteById(userId);
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_USER_IN_USE, userId));
		}
	}

	@Transactional
	public void changePassword(Long userId, UserChangePasswordInputDTO userChangePasswordDTO) {
		User user = findById(userId);
		user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
		repository.save(user);
	}

	@Transactional
	public Set<Role> getRoles(Long userId) {
		User user = findById(userId);
		return user.getRoles();
	}

	@Transactional
	public void grantRole(Long userId, Long roleId) {
		User user = findById(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().add(role);
		repository.save(user);
	}

	@Transactional
	public void revokeRole(Long userId, Long roleId) {
		User user = findById(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().remove(role);
		repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optional = repository.findByEmailWithRolesAndPrivileges(email);
		if (optional.isEmpty()) {
			logger.error("User not found: " + email);
			throw new UsernameNotFoundException("Username not found");
		}
		logger.info("User found: " + email);
		return optional.get();
	}
}
