package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import br.com.roanrobersson.rshop.api.v1.model.AddressModel;
import br.com.roanrobersson.rshop.api.v1.model.input.AddressInput;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.UserAddressControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.service.AddressService;

@RestController
@RequestMapping(value = "/v1/users/{userId}/addresses")
public class UserAddressController implements UserAddressControllerOpenApi {

	@Autowired
	private AddressService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<AddressModel>> list(@PathVariable UUID userId, Pageable pageable) {
		Page<Address> addresses = service.list(userId, pageable);
		Page<AddressModel> addressModels = addresses.map(x -> mapper.map(x, AddressModel.class));
		return ResponseEntity.ok(addressModels);
	}

	@GetMapping(value = "/{addressId}", produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AddressModel> findById(@PathVariable UUID userId, @PathVariable UUID addressId) {
		Address address = service.findById(userId, addressId);
		AddressModel addressModel = mapper.map(address, AddressModel.class);
		return ResponseEntity.ok().body(addressModel);
	}

	@GetMapping(value = "/main", produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AddressModel> findMain(@PathVariable UUID userId) {
		Address address = service.findMain(userId);
		AddressModel addressModel = mapper.map(address, AddressModel.class);
		return ResponseEntity.ok().body(addressModel);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AddressModel> insert(@PathVariable UUID userId,
			@Valid @RequestBody AddressInput addressInput) {
		Address address = service.insert(userId, addressInput);
		AddressModel addressModel = mapper.map(address, AddressModel.class);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(addressModel.getId())
				.toUri();
		return ResponseEntity.created(uri).body(addressModel);
	}

	@PutMapping(value = "/{addressId}", produces = "application/json")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<AddressModel> update(@PathVariable UUID userId, @PathVariable UUID addressId,
			@Valid @RequestBody AddressInput addressInput) {
		Address address = service.update(userId, addressId, addressInput);
		AddressModel addressModel = mapper.map(address, AddressModel.class);
		return ResponseEntity.ok().body(addressModel);
	}

	@DeleteMapping(value = "/{addressId}")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable UUID userId, @PathVariable UUID addressId) {
		service.delete(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{addressId}/main")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	public ResponseEntity<Void> setMain(@PathVariable UUID userId, @PathVariable UUID addressId) {
		service.setMain(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/main")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> unsetMain(@PathVariable UUID userId) {
		service.unsetMain(userId);
		return ResponseEntity.noContent().build();
	}
}
