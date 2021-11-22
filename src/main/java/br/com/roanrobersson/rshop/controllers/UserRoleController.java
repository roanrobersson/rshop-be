package br.com.roanrobersson.rshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roanrobersson.rshop.services.UserService;

@RestController
@RequestMapping(value = "/users/{userId}/roles")
public class UserRoleController {
	
	@Autowired
	UserService service;
	
	@PutMapping(value = "/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> grant(@PathVariable Long userId,  @PathVariable String roleId) {
		service.grantRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> revoke(@PathVariable Long userId,  @PathVariable String roleId) {
		service.revokeRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}
}