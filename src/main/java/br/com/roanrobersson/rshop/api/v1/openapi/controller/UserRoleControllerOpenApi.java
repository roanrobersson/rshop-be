package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Role")
public interface UserRoleControllerOpenApi {

	@Operation(summary = "Retrives the user roles list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Set<UUID>> findAll(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@Operation(summary = "Grants a role to a user")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Granted with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> grant(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "ID of a role", example = "18aace1e-f36a-4d71-b4d1-124387d9b63a") UUID roleId);

	@Operation(summary = "Revokes a role of a user")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Revoked with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> revoke(
			@Parameter(description = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@Parameter(description = "ID of a user role", example = "18aace1e-f36a-4d71-b4d1-124387d9b63a") UUID roleId);
}
