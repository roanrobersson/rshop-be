package br.com.roanrobersson.rshop.services;

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

import br.com.roanrobersson.rshop.dto.user.UserChangePasswordDTO;
import br.com.roanrobersson.rshop.dto.user.UserInsertDTO;
import br.com.roanrobersson.rshop.dto.user.UserResponseDTO;
import br.com.roanrobersson.rshop.dto.user.UserUpdateDTO;
import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.entities.User;
import br.com.roanrobersson.rshop.repositories.RoleRepository;
import br.com.roanrobersson.rshop.repositories.UserRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthService authService;
	
	private String defaultUserRoleId = "CLI";
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public Page<UserResponseDTO> findAllPaged(PageRequest pageRequest) {
		Page<User> list = repository.findAll(pageRequest);
		return list.map(x -> new UserResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserResponseDTO findById(Long userId) {
		authService.validateSelfOrAdmin(userId);
		User entity = findUserOrThrow(userId);
		return new UserResponseDTO(entity);
	}

	@Transactional
	public UserResponseDTO insert(UserInsertDTO dto) {
		User entity = modelMapper.map(dto, User.class);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		Role defaultRole = findRoleOrThrow(defaultUserRoleId);
		entity.getRoles().add(defaultRole);
		entity = repository.save(entity);
		return new UserResponseDTO(entity);
	}
	
	@Transactional
	public UserResponseDTO update(Long userId, UserUpdateDTO userUpdateDTO) {
		authService.validateSelfOrAdmin(userId);
		User entity = findUserOrThrow(userId);
		modelMapper.map(userUpdateDTO, entity);
		repository.save(entity);
		return new UserResponseDTO(entity);
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
		authService.validateSelfOrAdmin(userId);
		User entity = findUserOrThrow(userId);
		entity.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
		repository.save(entity);
	}
	
	@Transactional
	public Set<String> getRoles(Long userId) {	
		User entity = findUserOrThrow(userId);
		Set<String> set = entity.getRoles().stream().map(r -> r.getId()).collect(Collectors.toSet());
		return set;
	}
	
	@Transactional
	public void grantRole(Long userId, String roleId) {
		User userEntity = findUserOrThrow(userId);
		Role roleEntity = findRoleOrThrow(roleId);
		userEntity.getRoles().add(roleEntity);
		repository.save(userEntity);
	}
	
	@Transactional
	public void revokeRole(Long userId, String roleId) {
		User userEntity = findUserOrThrow(userId);
		Role roleEntity = findRoleOrThrow(roleId);
		userEntity.getRoles().remove(roleEntity);
		repository.save(userEntity);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("User found: " + username);
		return user;
	}
	
	private User findUserOrThrow(Long userId) {
		return repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	private Role findRoleOrThrow(String roleId) {
		return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
	}
}
