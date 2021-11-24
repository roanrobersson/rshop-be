package br.com.roanrobersson.rshop.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.roanrobersson.rshop.dto.address.AddressInsertDTO;
import br.com.roanrobersson.rshop.dto.address.AddressResponseDTO;
import br.com.roanrobersson.rshop.dto.address.AddressUpdateDTO;
import br.com.roanrobersson.rshop.services.UserAddressService;

@RestController
@RequestMapping(value = "/users/{userId}/addresses")
public class UserAddressController {

	@Autowired
	private UserAddressService userAddressService;
	
	@GetMapping(produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Page<AddressResponseDTO>> findAll(
			@PathVariable Long userId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesperpage", defaultValue = "5") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderby", defaultValue = "nick") String orderBy) {
		PageRequest request = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		Page<AddressResponseDTO> addressesPage = userAddressService.findAllPaged(userId, request);
		return ResponseEntity.ok(addressesPage);
	}
	
	@GetMapping(value = "/{id}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AddressResponseDTO> findById(@PathVariable Long userId, @PathVariable Long id) {
		AddressResponseDTO dto = userAddressService.findById(userId, id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AddressResponseDTO> create(@PathVariable Long userId, @Valid @RequestBody AddressInsertDTO dto) {
		AddressResponseDTO newDto = userAddressService.insert(userId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}", produces="application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AddressResponseDTO> update(@PathVariable Long userId, @PathVariable Long id, @Valid @RequestBody AddressUpdateDTO dto) {
		AddressResponseDTO newDto = userAddressService.update(userId, id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id) {
		userAddressService.delete(userId, id);
		return ResponseEntity.noContent().build();
	}
}
