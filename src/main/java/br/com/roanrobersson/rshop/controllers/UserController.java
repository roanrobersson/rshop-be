package br.com.roanrobersson.rshop.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.dto.user.UserChangePasswordDTO;
import br.com.roanrobersson.rshop.dto.user.UserInsertDTO;
import br.com.roanrobersson.rshop.dto.user.UserResponseDTO;
import br.com.roanrobersson.rshop.dto.user.UserUpdateDTO;
import br.com.roanrobersson.rshop.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
	public ResponseEntity<Page<UserResponseDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesperpage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderby", defaultValue = "firstName") String orderBy) {
		PageRequest request = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		Page<UserResponseDTO> usersPage = service.findAllPaged(request);
		return ResponseEntity.ok(usersPage);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		UserResponseDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
		UserResponseDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PatchMapping(value = "/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
		UserResponseDTO newDto = service.update(id, dto);
		return ResponseEntity.ok(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}/password")
	@PreAuthorize("isAuthenticated() or hasAnyRole('ADMIN')")
	public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody UserChangePasswordDTO dto) {
		service.changePassword(id, dto);
		return ResponseEntity.noContent().build();
	}
}