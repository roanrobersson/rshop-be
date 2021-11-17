package br.com.roanrobersson.rshop.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${default_user_role_id}")
	private Long defaultUserRoleId;
	
	@Transactional(readOnly = true)
	public Page<UserResponseDTO> findAllPaged(PageRequest pageRequest) {
		Page<User> list = repository.findAll(pageRequest);
		return list.map(x -> new UserResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserResponseDTO findById(Long id) {
		authService.validateSelfOrAdmin(id);
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
		return new UserResponseDTO(entity);
	}

	@Transactional
	public UserResponseDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		Role defaultRole = roleRepository.getById(defaultUserRoleId);
		entity.getRoles().add(defaultRole);
		entity = repository.save(entity);
		return new UserResponseDTO(entity);
	}
	
	@Transactional
	public UserResponseDTO update(Long id, UserUpdateDTO dto) {
		authService.validateSelfOrAdmin(id);
		try {
			User entity = repository.getById(id);
			copyDtoToEntity(dto, entity);
			repository.save(entity);
			return new UserResponseDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	@Transactional
	public void changePassword(Long id, UserChangePasswordDTO dto) {
		authService.validateSelfOrAdmin(id);
		try {
			User entity = repository.getById(id);
			entity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
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
	
	private void copyDtoToEntity(UserInsertDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
	}
	
	private void copyDtoToEntity(UserUpdateDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
	}
}
