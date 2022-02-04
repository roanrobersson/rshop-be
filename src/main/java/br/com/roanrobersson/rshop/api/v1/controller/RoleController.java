package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.api.v1.dto.RoleDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/roles")
@Api(tags = "Role")
public class RoleController {

	@Autowired
	RoleService service;

	@Autowired
	private RoleMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives the roles list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<List<RoleDTO>> getRoles(
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		Sort sort = Sort.by(new Order(Direction.fromString(direction), orderBy));
		List<Role> roles = service.findAll(sort);
		List<RoleDTO> roleResponseDTOs = roles.stream().map(role -> mapper.toRoleDTO(role))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(roleResponseDTOs);
	}

	@GetMapping(value = "/{roleId}", produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives a role by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<RoleDTO> findById(@PathVariable Long roleId) {
		Role role = service.findById(roleId);
		RoleDTO roleResponseDTO = mapper.toRoleDTO(role);
		return ResponseEntity.ok().body(roleResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a new role")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<RoleDTO> insert(@Valid @RequestBody RoleInputDTO roleInputDTO) {
		Role role = service.insert(roleInputDTO);
		RoleDTO roleResponseDTO = mapper.toRoleDTO(role);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(roleResponseDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(roleResponseDTO);
	}

	@PutMapping(value = "/{roleId}", produces = "application/json")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updates an existing role")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<RoleDTO> update(@PathVariable Long roleId, @Valid @RequestBody RoleInputDTO roleInputDTO) {
		Role role = service.update(roleId, roleInputDTO);
		RoleDTO roleResponseDTO = mapper.toRoleDTO(role);
		return ResponseEntity.ok().body(roleResponseDTO);
	}

	@DeleteMapping(value = "/{roleId}")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Removes an existing role")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(@PathVariable Long roleId) {
		service.delete(roleId);
		return ResponseEntity.noContent().build();
	}
}
