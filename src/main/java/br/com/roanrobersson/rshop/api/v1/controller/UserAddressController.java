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

import br.com.roanrobersson.rshop.api.v1.openapi.controller.UserAddressControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.dto.input.AddressInput;
import br.com.roanrobersson.rshop.domain.dto.model.AddressModel;
import br.com.roanrobersson.rshop.domain.mapper.AddressMapper;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.service.UserService;

@RestController
@RequestMapping(value = "/v1/users/{userId}/addresses")
public class UserAddressController implements UserAddressControllerOpenApi {

	@Autowired
	private UserService service;

	@Autowired
	private AddressMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<AddressModel>> list(@PathVariable UUID userId, Pageable pageable) {
		Page<Address> addresses = service.listAddresses(userId, pageable);
		Page<AddressModel> addressModels = mapper.toModelPage(addresses);
		return ResponseEntity.ok(addressModels);
	}

	@GetMapping(value = "/{addressId}", produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AddressModel> findById(@PathVariable UUID userId, @PathVariable UUID addressId) {
		Address address = service.findAddressById(userId, addressId);
		AddressModel addressModel = mapper.toModel(address);
		return ResponseEntity.ok().body(addressModel);
	}

	@GetMapping(value = "/main", produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AddressModel> findMain(@PathVariable UUID userId) {
		Address address = service.findMainAddress(userId);
		AddressModel addressModel = mapper.toModel(address);
		return ResponseEntity.ok().body(addressModel);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AddressModel> insert(@PathVariable UUID userId,
			@Valid @RequestBody AddressInput addressInput) {
		Address address = service.insertAddress(userId, addressInput);
		AddressModel addressModel = mapper.toModel(address);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(addressModel.getId())
				.toUri();
		return ResponseEntity.created(uri).body(addressModel);
	}

	@PutMapping(value = "/{addressId}", produces = "application/json")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<AddressModel> update(@PathVariable UUID userId, @PathVariable UUID addressId,
			@Valid @RequestBody AddressInput addressInput) {
		Address address = service.updateAddress(userId, addressId, addressInput);
		AddressModel addressModel = mapper.toModel(address);
		return ResponseEntity.ok().body(addressModel);
	}

	@DeleteMapping(value = "/{addressId}")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable UUID userId, @PathVariable UUID addressId) {
		service.deleteAddress(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{addressId}/main")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<Void> setMain(@PathVariable UUID userId, @PathVariable UUID addressId) {
		service.setMainAddress(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/main")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> unsetMain(@PathVariable UUID userId) {
		service.unsetMainAddress(userId);
		return ResponseEntity.noContent().build();
	}
}
