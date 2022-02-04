package br.com.roanrobersson.rshop.api.v1.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/users/{userId}/roles")
@Api(tags = "User Role")
public class UserRoleController {

	@Autowired
	UserService service;

	@GetMapping
	@CheckSecurity.UserRole.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives the user roles list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Set<Long>> findAll(@PathVariable Long userId) {
		Set<Long> rolesIds = service.getRoles(userId).stream().map(Role::getId).collect(Collectors.toSet());
		return ResponseEntity.ok().body(rolesIds);
	}

	@PutMapping(value = "/{roleId}")
	@CheckSecurity.UserRole.CanGrantAndRevoke
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Grants a role to a user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Granted with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> grant(@PathVariable Long userId, @PathVariable Long roleId) {
		service.grantRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{roleId}")
	@CheckSecurity.UserRole.CanGrantAndRevoke
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Revokes a role of a user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Revoked with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> revoke(@PathVariable Long userId, @PathVariable Long roleId) {
		service.revokeRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}
}