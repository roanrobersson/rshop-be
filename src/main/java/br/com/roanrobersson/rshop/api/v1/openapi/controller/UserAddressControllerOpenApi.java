package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.dto.AddressDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.AddressInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "UserAddress")
public interface UserAddressControllerOpenApi {

	@ApiOperation("Retrives a addresses page by User ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<List<AddressDTO>> findAllByUserId(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId,
			@ApiParam(value = "Sort direction", example = "DESC", required = false) String direction,
			@ApiParam(value = "Address property to orderby", example = "uf", required = false) String orderBy);

	@ApiOperation("Retrives a address by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> findById(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId,
			@ApiParam(value = "ID of user address", example = "5b79649cf311465fb2d907355b56d08a") UUID addressId);

	@ApiOperation("Retrives the main address")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> findMain(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId);

	@ApiOperation("Creates a new address")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> insert(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId,
			AddressInputDTO addressInputDTO);

	@ApiOperation(value = "Updates an existing address")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<AddressDTO> update(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId,
			@ApiParam(value = "ID of user address", example = "5b79649cf311465fb2d907355b56d08a") UUID addressId,
			AddressInputDTO addressInputDTO);

	@ApiOperation("Removes an existing address")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId,
			@ApiParam(value = "ID of user address", example = "5b79649cf311465fb2d907355b56d08a") UUID addressId);

	@ApiOperation("Sets an address as default")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Setted with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> setMain(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId,
			@ApiParam(value = "ID of user address", example = "5b79649cf311465fb2d907355b56d08a") UUID addressId);

	@ApiOperation("Unsets the default address")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Unsetted with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> unsetMain(
			@ApiParam(value = "ID of a user", example = "5b79649cf311465fb2d907355b56d08a") UUID userId);
}
