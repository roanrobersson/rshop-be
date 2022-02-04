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
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

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
		return findUserOrThrow(userId);
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return findUserOrThrow(email);
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
		User user = findUserOrThrow(userId);
		mapper.update(userUpdateDTO, user);
		return repository.save(user);
	}

	public void delete(Long userId) {
		try {
			repository.deleteById(userId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + userId + " not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	@Transactional
	public void changePassword(Long userId, UserChangePasswordInputDTO userChangePasswordDTO) {
		User user = findUserOrThrow(userId);
		user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
		repository.save(user);
	}

	@Transactional
	public Set<Role> getRoles(Long userId) {
		User user = findUserOrThrow(userId);
		return user.getRoles();
	}

	@Transactional
	public void grantRole(Long userId, Long roleId) {
		User user = findUserOrThrow(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().add(role);
		repository.save(user);
	}

	@Transactional
	public void revokeRole(Long userId, Long roleId) {
		User user = findUserOrThrow(userId);
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

	private User findUserOrThrow(Long userId) {
		return repository.findByIdWithRolesAndPrivileges(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found"));
	}

	private User findUserOrThrow(String email) {
		return repository.findByEmailWithRolesAndPrivileges(email)
				.orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
	}
}
