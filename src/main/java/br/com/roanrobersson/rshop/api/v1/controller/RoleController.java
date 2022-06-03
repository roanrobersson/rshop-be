package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.api.v1.model.CountModel;
import br.com.roanrobersson.rshop.api.v1.model.RoleModel;
import br.com.roanrobersson.rshop.api.v1.model.input.RoleInput;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.RoleControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.PrivilegeNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.RoleService;

@RestController
@RequestMapping(value = "/v1/roles")
public class RoleController implements RoleControllerOpenApi {

	@Autowired
	RoleService service;

	@Autowired
	private RoleMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<RoleModel>> list(Pageable pageable) {
		Page<Role> roles = service.list(pageable);
		Page<RoleModel> roleModels = roles.map(role -> mapper.toRoleModel(role));
		return ResponseEntity.ok().body(roleModels);
	}

	@GetMapping(value = "/{roleId}", produces = "application/json")
	@CheckSecurity.Role.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RoleModel> findById(@PathVariable UUID roleId) {
		Role role = service.findById(roleId);
		RoleModel roleModel = mapper.toRoleModel(role);
		return ResponseEntity.ok().body(roleModel);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RoleModel> insert(@Valid @RequestBody RoleInput roleInput) {
		try {
			Role role = service.insert(roleInput);
			RoleModel roleModel = mapper.toRoleModel(role);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(roleModel.getId())
					.toUri();
			return ResponseEntity.created(uri).body(roleModel);
		} catch (PrivilegeNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping(value = "/{roleId}", produces = "application/json")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RoleModel> update(@PathVariable UUID roleId, @Valid @RequestBody RoleInput roleInput) {
		try {
			Role role = service.update(roleId, roleInput);
			RoleModel roleModel = mapper.toRoleModel(role);
			return ResponseEntity.ok().body(roleModel);
		} catch (PrivilegeNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{roleId}")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable UUID roleId) {
		service.delete(roleId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/count", produces = "application/json")
	@CheckSecurity.Role.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CountModel> count() {
		CountModel countModel = new CountModel(service.count());
		return ResponseEntity.ok().body(countModel);
	}
}
