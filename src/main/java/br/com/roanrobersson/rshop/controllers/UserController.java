package br.com.roanrobersson.rshop.controllers;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.config.CheckSecurity;
import br.com.roanrobersson.rshop.domain.dto.UserChangePasswordDTO;
import br.com.roanrobersson.rshop.domain.dto.UserDTO;
import br.com.roanrobersson.rshop.domain.dto.UserInsertDTO;
import br.com.roanrobersson.rshop.domain.dto.UserUpdateDTO;
import br.com.roanrobersson.rshop.domain.entities.User;
import br.com.roanrobersson.rshop.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.User.CanConsult
	public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesperpage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderby", defaultValue = "email") String orderBy) {
		PageRequest request = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		Page<User> userPage = service.findAllPaged(request);
		Page<UserDTO> userResponseDTOs = userPage.map(x -> convertToDto(x));
		return ResponseEntity.ok(userResponseDTOs);
	}

	@GetMapping(value = "/{userId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.User.CanConsult
	public ResponseEntity<UserDTO> findById(@PathVariable Long userId) {
		User user = service.findById(userId);
		UserDTO userResponseDTO = new UserDTO();
		copyEntityToDto(user, userResponseDTO);
		return ResponseEntity.ok(userResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@CheckSecurity.User.CanEdit
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO userInsertDTO) {
		User user = service.insert(userInsertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(user.getId()).toUri();
		UserDTO userResponseDTO = new UserDTO();
		copyEntityToDto(user, userResponseDTO);
		return ResponseEntity.created(uri).body(userResponseDTO);
	}

	@PatchMapping(value = "/{userId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.User.CanEdit
	public ResponseEntity<UserDTO> update(@PathVariable Long userId,
			@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
		User user = service.update(userId, userUpdateDTO);
		UserDTO userResponseDTO = new UserDTO();
		copyEntityToDto(user, userResponseDTO);
		return ResponseEntity.ok(userResponseDTO);
	}

	@DeleteMapping(value = "/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.User.CanEdit
	public ResponseEntity<Void> delete(@PathVariable Long userId) {
		service.delete(userId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{userId}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.User.CanEdit
	public ResponseEntity<Void> changePassword(@PathVariable Long userId,
			@Valid @RequestBody UserChangePasswordDTO userChangePasswordDTO) {
		service.changePassword(userId, userChangePasswordDTO);
		return ResponseEntity.noContent().build();
	}
	
	private void copyEntityToDto(User user, UserDTO userDTO) {
		mapper.map(user, userDTO);
		Set<Long> rolesIds = user.getRoles().stream().map((x) -> x.getId()).collect(Collectors.toSet());
		Long imageId = user.getImage() != null ? user.getImage().getId() : null;
		userDTO.setImageId(imageId);
		userDTO.setRolesIds(rolesIds);
	}
	
	private UserDTO convertToDto(User user) {
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		Set<Long> rolesIds = user.getRoles().stream().map((x) -> x.getId()).collect(Collectors.toSet());
		Long imageId = user.getImage() != null ? user.getImage().getId() : null;
		userDTO.setRolesIds(rolesIds);
		userDTO.setImageId(imageId);
		return userDTO;
	}
}