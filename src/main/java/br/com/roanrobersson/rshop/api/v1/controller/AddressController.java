package br.com.roanrobersson.rshop.api.v1.controller;

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

import br.com.roanrobersson.rshop.api.v1.dto.AddressDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.AddressInputDTO;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/users/{userId}/addresses")
@Api(tags = "Address")
public class AddressController {

	@Autowired
	private AddressService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives a addresses page by User ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<List<AddressDTO>> findAllByUserId(@PathVariable Long userId,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderby", defaultValue = "nick") String orderBy) {
		Sort sort = Sort.by(new Order(Direction.fromString(direction), orderBy));
		List<Address> addresses = service.findAll(userId, sort);
		List<AddressDTO> addressDTOs = addresses.stream().map(x -> mapper.map(x, AddressDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok(addressDTOs);
	}

	@GetMapping(value = "/{addressId}", produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives a address by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> findById(@PathVariable Long userId, @PathVariable Long addressId) {
		Address address = service.findById(userId, addressId);
		AddressDTO addressResponseDTO = mapper.map(address, AddressDTO.class);
		return ResponseEntity.ok().body(addressResponseDTO);
	}

	@GetMapping(value = "/main", produces = "application/json")
	@CheckSecurity.Address.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives the main address")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> findMain(@PathVariable Long userId) {
		Address address = service.findMain(userId);
		AddressDTO addressResponseDTO = mapper.map(address, AddressDTO.class);
		return ResponseEntity.ok().body(addressResponseDTO);
	}
	
	@PostMapping(produces = "application/json")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a new address")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> insert(@PathVariable Long userId,
			@Valid @RequestBody AddressInputDTO addressInputDTO) {
		Address address = service.insert(userId, addressInputDTO);
		AddressDTO addressResponseDTO = mapper.map(address, AddressDTO.class);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(addressResponseDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(addressResponseDTO);
	}

	@PutMapping(value = "/{addressId}", produces = "application/json")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Updates an existing address")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> update(@PathVariable Long userId, @PathVariable Long addressId,
			@Valid @RequestBody AddressInputDTO addressInputDTO) {
		Address address = service.update(userId, addressId, addressInputDTO);
		AddressDTO addressResponseDTO = mapper.map(address, AddressDTO.class);
		return ResponseEntity.ok().body(addressResponseDTO);
	}

	@DeleteMapping(value = "/{addressId}")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Removes an existing address")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long addressId) {
		service.delete(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{addressId}/main")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Address.CanEdit
	@ApiOperation(value = "Sets an address as default")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Setted with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> setMain(@PathVariable Long userId, @PathVariable Long addressId) {
		service.setMain(userId, addressId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/main")
	@CheckSecurity.Address.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Unsets the default address")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Unsetted with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> unsetMain(@PathVariable Long userId) {
		service.unsetMain(userId);
		return ResponseEntity.noContent().build();
	}
}
