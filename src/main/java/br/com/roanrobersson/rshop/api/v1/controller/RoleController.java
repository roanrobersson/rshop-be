package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
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

import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
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
	public ResponseEntity<List<RoleModel>> getRoles(
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		Sort sort = Sort.by(new Order(Direction.fromString(direction), orderBy));
		List<Role> roles = service.findAll(sort);
		List<RoleModel> roleModels = roles.stream().map(role -> mapper.toRoleModel(role)).collect(Collectors.toList());
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
}
