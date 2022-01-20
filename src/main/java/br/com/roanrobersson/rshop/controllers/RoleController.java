package br.com.roanrobersson.rshop.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import br.com.roanrobersson.rshop.config.CheckSecurity;
import br.com.roanrobersson.rshop.domain.dto.RoleDTO;
import br.com.roanrobersson.rshop.domain.entities.Role;
import br.com.roanrobersson.rshop.services.RoleService;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

	@Autowired
	RoleService service;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Role.CanConsult
	public ResponseEntity<List<RoleDTO>> getRoles(
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		System.out.println("vaca");
		Sort sort = Sort.by(new Order(Direction.fromString(direction), orderBy));
		List<Role> roles = service.findAll(sort);
		List<RoleDTO> roleResponseDTOs = roles.stream().map(x -> mapper.map(x, RoleDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(roleResponseDTOs);
	}

	@GetMapping(value = "/{roleId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Role.CanConsult
	public ResponseEntity<RoleDTO> findById(@PathVariable Long roleId) {
		Role role = service.findById(roleId);
		RoleDTO roleResponseDTO = mapper.map(role, RoleDTO.class);
		return ResponseEntity.ok().body(roleResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@CheckSecurity.Role.CanEdit
	public ResponseEntity<RoleDTO> insert(@Valid @RequestBody RoleDTO roleDTO) {
		Role role = service.insert(roleDTO);
		RoleDTO roleResponseDTO = mapper.map(role, RoleDTO.class);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(roleResponseDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(roleResponseDTO);
	}

	@PutMapping(value = "/{roleId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Role.CanEdit
	public ResponseEntity<RoleDTO> update(@PathVariable Long roleId, @Valid @RequestBody RoleDTO roleDTO) {
		Role role = service.update(roleId, roleDTO);
		RoleDTO roleResponseDTO = mapper.map(role, RoleDTO.class);
		return ResponseEntity.ok().body(roleResponseDTO);
	}

	@DeleteMapping(value = "/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Role.CanEdit
	public ResponseEntity<Void> delete(@PathVariable Long roleId) {
		service.delete(roleId);
		return ResponseEntity.noContent().build();
	}
}
