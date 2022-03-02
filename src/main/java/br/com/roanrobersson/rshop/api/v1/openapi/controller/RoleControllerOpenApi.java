package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.RoleModel;
import br.com.roanrobersson.rshop.api.v1.model.input.RoleInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Role")
public interface RoleControllerOpenApi {

	@Operation(summary = "Retrives the roles list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<RoleModel>> getRoles(
			@Parameter(description = "Sort direction", example = "DESC", required = false) String direction,
			@Parameter(description = "Property to orderby", example = "name", required = false) String orderBy);

	@Operation(summary = "Retrives a role by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<RoleModel> findById(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId);

	@Operation(summary = "Creates a new role")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<RoleModel> insert(RoleInput roleInputDTO);

	@Operation(summary = "Updates an existing role")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<RoleModel> update(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId,
			RoleInput roleInputDTO);

	@Operation(summary = "Removes an existing role")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Removed with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> delete(UUID roleId);
}
