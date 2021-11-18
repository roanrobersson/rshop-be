package br.com.roanrobersson.rshop.controllers;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.dto.role.RoleInsertDTO;
import br.com.roanrobersson.rshop.dto.role.RoleResponseDTO;
import br.com.roanrobersson.rshop.dto.role.RoleUpdateDTO;
import br.com.roanrobersson.rshop.services.RoleService;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

	@Autowired
	RoleService service;
	
	@GetMapping(produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<RoleResponseDTO>> getRoles(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction,
		@RequestParam(value = "orderBy", defaultValue = "authority") String orderBy
		) {
	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
	
	Page<RoleResponseDTO> list = service.findAllPaged(pageRequest);
	return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RoleResponseDTO> findById(@PathVariable Long id) {
		RoleResponseDTO newDto = service.findById(id);
		return ResponseEntity.ok().body(newDto);
	}
	
	@PostMapping(produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RoleResponseDTO> insert(@Valid @RequestBody RoleInsertDTO dto) {
		RoleResponseDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RoleResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RoleUpdateDTO dto) {
		RoleResponseDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
