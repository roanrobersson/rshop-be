package br.com.roanrobersson.rshop.controllers;

import java.util.Set;

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

import br.com.roanrobersson.rshop.config.CheckSecurity;
import br.com.roanrobersson.rshop.services.UserService;

@RestController
@RequestMapping(value = "/users/{userId}/roles")
public class UserRoleController {

	@Autowired
	UserService service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.UserRole.CanConsult
	public ResponseEntity<Set<Long>> findAll(@PathVariable Long userId) {
		Set<Long> rolesIds = service.getRoles(userId);
		return ResponseEntity.ok().body(rolesIds);
	}

	@PutMapping(value = "/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.UserRole.CanGrantAndRevoke
	public ResponseEntity<Void> grant(@PathVariable Long userId, @PathVariable Long roleId) {
		service.grantRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.UserRole.CanGrantAndRevoke
	public ResponseEntity<Void> revoke(@PathVariable Long userId, @PathVariable Long roleId) {
		service.revokeRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}
}