package br.com.roanrobersson.rshop.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import br.com.roanrobersson.rshop.domain.dto.UserChangePasswordDTO;
import br.com.roanrobersson.rshop.domain.dto.UserInsertDTO;
import br.com.roanrobersson.rshop.domain.dto.UserUpdateDTO;
import br.com.roanrobersson.rshop.domain.entities.Role;
import br.com.roanrobersson.rshop.domain.entities.User;
import br.com.roanrobersson.rshop.repositories.UserRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

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
	private ModelMapper mapper;

	private Long defaultUserRoleId = 1L;

	@Transactional(readOnly = true)
	public Page<User> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Transactional(readOnly = true)
	public User findById(Long userId) {
		return findUserOrThrow(userId);
	}

	@Transactional
	public User insert(UserInsertDTO userInsertDTO) {
		User user = new User();
		copyDtoToEntity(userInsertDTO, user);
		return repository.save(user);
	}

	@Transactional
	public User update(Long userId, UserUpdateDTO userUpdateDTO) {
		User user = findUserOrThrow(userId);
		mapper.map(userUpdateDTO, user);
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
	public void changePassword(Long userId, UserChangePasswordDTO userChangePasswordDTO) {
		User user = findUserOrThrow(userId);
		user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
		repository.save(user);
	}

	@Transactional
	public Set<Long> getRoles(Long userId) {
		User user = findUserOrThrow(userId);
		return user.getRoles().stream().map(r -> r.getId()).collect(Collectors.toSet());
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
		Optional<User> optional = repository.findByEmail(email);
		if (optional.isEmpty()) {
			logger.error("User not found: " + email);
			throw new UsernameNotFoundException("Username not found");
		}
		logger.info("User found: " + email);
		return optional.get();
	}

	private User findUserOrThrow(Long userId) {
		return repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found"));
	}

	private void copyDtoToEntity(UserInsertDTO userInsertDTO, User user) {
		Role defaultRole = roleService.findById(defaultUserRoleId);
		mapper.map(userInsertDTO, user);
		user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
		user.getRoles().add(defaultRole);
	}
}
