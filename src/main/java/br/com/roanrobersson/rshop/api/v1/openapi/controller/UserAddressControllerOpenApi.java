package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.AddressModel;
import br.com.roanrobersson.rshop.api.v1.model.input.AddressInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Address")
public interface UserAddressControllerOpenApi {

	@Operation(summary = "Retrives a addresses page by User ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<AddressModel>> findAllByUserId(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "Sort direction", example = "DESC", required = false) String direction,
			@Parameter(description = "Address property to orderby", example = "uf", required = false) String orderBy);

	@Operation(summary = "Retrives a address by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<AddressModel> findById(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") UUID addressId);

	@Operation(summary = "Retrives the main address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<AddressModel> findMain(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@Operation(summary = "Creates a new address")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<AddressModel> insert(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			AddressInput addressInputDTO);

	@Operation(summary = "Updates an existing address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<AddressModel> update(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") UUID addressId,
			AddressInput addressInputDTO);

	@Operation(summary = "Removes an existing address")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Removed with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> delete(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") UUID addressId);

	@Operation(summary = "Sets an address as default")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Setted with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> setMain(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") UUID addressId);

	@Operation(summary = "Unsets the default address")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Unsetted with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> unsetMain(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);
}
