package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;

import javax.validation.Valid;

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

import br.com.roanrobersson.rshop.api.v1.dto.UserDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserChangePasswordInputDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/users")
@Api(tags = "User")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.User.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives a users page")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesperpage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderby", defaultValue = "email") String orderBy) {
		PageRequest request = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		Page<User> userPage = service.findAllPaged(request);
		Page<UserDTO> userResponseDTOs = userPage.map(x -> mapper.toUserDTO(x));
		return ResponseEntity.ok(userResponseDTOs);
	}

	@GetMapping(value = "/{userId}", produces = "application/json")
	@CheckSecurity.User.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives a user by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<UserDTO> findById(@PathVariable Long userId) {
		User user = service.findById(userId);
		UserDTO userResponseDTO = new UserDTO();
		mapper.update(user, userResponseDTO);
		return ResponseEntity.ok(userResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a new user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO userInsertDTO) {
		User user = service.insert(userInsertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(user.getId()).toUri();
		UserDTO userResponseDTO = new UserDTO();
		mapper.update(user, userResponseDTO);
		return ResponseEntity.created(uri).body(userResponseDTO);
	}

	@PatchMapping(value = "/{userId}", produces = "application/json")
	@CheckSecurity.User.CanEdit
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updates an existing user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<UserDTO> update(@PathVariable Long userId, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
		User user = service.update(userId, userUpdateDTO);
		UserDTO userResponseDTO = new UserDTO();
		mapper.update(user, userResponseDTO);
		return ResponseEntity.ok(userResponseDTO);
	}

	@DeleteMapping(value = "/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.User.CanEdit
	@ApiOperation(value = "Removes an existing user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(@PathVariable Long userId) {
		service.delete(userId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{userId}/password")
	@CheckSecurity.User.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Changes a user's password")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Changed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> changePassword(@PathVariable Long userId,
			@Valid @RequestBody UserChangePasswordInputDTO userChangePasswordDTO) {
		service.changePassword(userId, userChangePasswordDTO);
		return ResponseEntity.noContent().build();
	}
}