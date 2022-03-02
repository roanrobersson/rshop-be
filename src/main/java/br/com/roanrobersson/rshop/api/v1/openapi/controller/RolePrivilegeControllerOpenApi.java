package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Role Privilege")
public interface RolePrivilegeControllerOpenApi {

	@Operation(summary = "Retrives the role privileges list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Set<UUID>> findAll(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId);

	@Operation(summary = "Link a privilege to a role")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Link with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> link(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId,
			@Parameter(description = "ID of a privilege", example = "585a242f-1058-476c-8656-eafe1fed5812") UUID privilegeId);

	@Operation(summary = "Unlink a privilege from a role")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Unlink with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> unlink(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId,
			@Parameter(description = "ID of a privilege", example = "585a242f-1058-476c-8656-eafe1fed5812") UUID privilegeId);
}
