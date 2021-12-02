package br.com.roanrobersson.rshop.controllers;

import java.net.URI;
import java.util.List;
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

import br.com.roanrobersson.rshop.config.CheckSecurity;
import br.com.roanrobersson.rshop.dto.AddressDTO;
import br.com.roanrobersson.rshop.dto.response.AddressResponseDTO;
import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.services.AddressService;

@RestController
@RequestMapping(value = "/users/{userId}/addresses")
public class AddressController {

	@Autowired
	private AddressService service;

	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Address.CanConsult
	public ResponseEntity<List<AddressResponseDTO>> findAllByUserId(@PathVariable Long userId,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderby", defaultValue = "nick") String orderBy) {
		Sort sort = Sort.by(new Order(Direction.fromString(direction), orderBy));
		List<Address> addressList = service.findAll(userId, sort);
		List<AddressResponseDTO> addressDtoList = addressList.stream().map(x -> new AddressResponseDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok(addressDtoList);
	}

	@GetMapping(value = "/{addressId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Address.CanConsult
	public ResponseEntity<AddressResponseDTO> findById(@PathVariable Long userId, @PathVariable Long addressId) {
		Address address = service.findById(userId, addressId);
		AddressResponseDTO addressResponseDTO = new AddressResponseDTO(address);
		return ResponseEntity.ok().body(addressResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<AddressResponseDTO> create(@PathVariable Long userId,
			@Valid @RequestBody AddressDTO addressDTO) {
		Address address = service.insert(userId, addressDTO);
		AddressResponseDTO addressResponseDTO = new AddressResponseDTO(address);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(addressResponseDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(addressResponseDTO);
	}

	@PutMapping(value = "/{addressId}", produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<AddressResponseDTO> update(@PathVariable Long userId, @PathVariable Long addressId,
			@Valid @RequestBody AddressDTO addressDTO) {
		Address address = service.update(userId, addressId, addressDTO);
		AddressResponseDTO addressResponseDTO = new AddressResponseDTO(address);
		return ResponseEntity.ok().body(addressResponseDTO);
	}

	@DeleteMapping(value = "/{addressId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long addressId) {
		service.delete(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{addressId}/main")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<Void> setMain(@PathVariable Long userId, @PathVariable Long addressId) {
		service.setMain(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/main")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<Void> unsetMain(@PathVariable Long userId) {
		service.unsetMain(userId);
		return ResponseEntity.noContent().build();
	}
}
