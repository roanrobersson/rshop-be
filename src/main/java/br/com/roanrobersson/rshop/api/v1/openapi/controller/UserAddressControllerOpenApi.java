package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.domain.dto.input.AddressInput;
import br.com.roanrobersson.rshop.domain.dto.model.AddressModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Address")
public interface UserAddressControllerOpenApi {

	@Operation(summary = "Retrives a addresses page by User ID", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Addresses retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Page<AddressModel>> list(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId,
			Pageable pageable);

	@Operation(summary = "Retrives a address by ID", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Address retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid address ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Address not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<AddressModel> findById(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") Long addressId);

	@Operation(summary = "Retrives the main address", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main address retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid address ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Address not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<AddressModel> findMain(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId);

	@Operation(summary = "Creates a new address", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Address created with success"),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<AddressModel> insert(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId,
			AddressInput addressInputDTO);

	@Operation(summary = "Updates an existing address", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Address updated with success"),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<AddressModel> update(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") Long addressId,
			AddressInput addressInputDTO);

	@Operation(summary = "Removes an existing address", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Address removed with success"),
			@ApiResponse(responseCode = "400", description = "Invalid address ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Address not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> delete(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") Long addressId);

	@Operation(summary = "Sets an address as default", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Setted with success"),
			@ApiResponse(responseCode = "400", description = "Invalid address ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Address not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> setMain(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId,
			@Parameter(description = "ID of user address", example = "6353293a-d2b6-400f-997d-d6935032a52f") Long addressId);

	@Operation(summary = "Unsets the default address", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Unsetted with success"),
			@ApiResponse(responseCode = "400", description = "Invalid address ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Address not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> unsetMain(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") Long userId);
}
