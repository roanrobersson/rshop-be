package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.api.v1.model.UserModel;
import br.com.roanrobersson.rshop.api.v1.model.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User")
public interface UserControllerOpenApi {

	@Operation(summary = "Retrives a users page", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Users retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Page<UserModel>> findAll(
			@Parameter(description = "Page number", example = "3", required = false) Integer page,
			@Parameter(description = "Register per page", example = "15", required = false) Integer linesPerPage,
			@Parameter(description = "Sort direction", example = "DESC", required = false) String direction,
			@Parameter(description = "Property to orderby", example = "uf", required = false) String orderBy);

	@Operation(summary = "Retrives a user by ID", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<UserModel> findById(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@Operation(summary = "Creates a new user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User created with success"),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<UserModel> insert(
			@Parameter(description = "Representation of a new user") UserInsert userInsertDTO);

	@Operation(summary = "Updates an existing user", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User updated with success"),
			@ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<UserModel> update(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "Representation of a user with the new data") UserUpdate userUpdateDTO);

	@Operation(summary = "Removes an existing user", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Removed with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> delete(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@Operation(summary = "Changes a user's password", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "User password changed with success"),
			@ApiResponse(responseCode = "400", description = "Invalid user ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> changePassword(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "Representation of user new password") UserChangePasswordInput userChangePasswordDTO);
}
