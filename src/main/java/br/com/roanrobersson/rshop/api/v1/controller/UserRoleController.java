package br.com.roanrobersson.rshop.api.v1.controller;

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

import br.com.roanrobersson.rshop.api.v1.openapi.controller.UserRoleControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.dto.model.RoleModel;
import br.com.roanrobersson.rshop.domain.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.UserService;

@RestController
@RequestMapping(value = "/v1/users/{userId}/roles")
public class UserRoleController implements UserRoleControllerOpenApi {

	@Autowired
	UserService service;

	@Autowired
	RoleMapper roleMapper;

	@GetMapping
	@CheckSecurity.UserRole.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Set<RoleModel>> list(@PathVariable Long userId) {
		Set<Role> roles = service.listRoles(userId);
		Set<RoleModel> roleModels = roleMapper.toModelSet(roles);
		return ResponseEntity.ok().body(roleModels);
	}

	@PutMapping(value = "/{roleId}")
	@CheckSecurity.UserRole.CanGrantAndRevoke
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> grant(@PathVariable Long userId, @PathVariable Long roleId) {
		service.grantRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{roleId}")
	@CheckSecurity.UserRole.CanGrantAndRevoke
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> revoke(@PathVariable Long userId, @PathVariable Long roleId) {
		service.revokeRole(userId, roleId);
		return ResponseEntity.noContent().build();
	}
}