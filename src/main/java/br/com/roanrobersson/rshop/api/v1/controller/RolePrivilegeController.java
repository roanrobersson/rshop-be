package br.com.roanrobersson.rshop.api.v1.controller;

import java.util.Set;
import java.util.UUID;
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

import br.com.roanrobersson.rshop.api.v1.openapi.controller.RolePrivilegeControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.service.RoleService;

@RestController
@RequestMapping(value = "/v1/roles/{roleId}/privileges")
public class RolePrivilegeController implements RolePrivilegeControllerOpenApi {

	@Autowired
	RoleService service;

	@GetMapping
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Set<UUID>> list(@PathVariable UUID roleId) {
		Set<UUID> privilegesIds = service.getPrivileges(roleId).stream().map(Privilege::getId)
				.collect(Collectors.toSet());
		return ResponseEntity.ok().body(privilegesIds);
	}

	@PutMapping(value = "/{privilegeId}")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> link(@PathVariable UUID roleId, @PathVariable UUID privilegeId) {
		service.linkPrivilege(roleId, privilegeId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{privilegeId}")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> unlink(@PathVariable UUID roleId, @PathVariable UUID privilegeId) {
		service.unlinkPrivilege(roleId, privilegeId);
		return ResponseEntity.noContent().build();
	}
}
