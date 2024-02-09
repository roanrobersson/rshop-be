package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.domain.dto.input.RoleInput;
import br.com.roanrobersson.rshop.domain.dto.model.CountModel;
import br.com.roanrobersson.rshop.domain.dto.model.RoleModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Role")
public interface RoleControllerOpenApi {

	@Operation(summary = "Retrive a role list", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Roles retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Page<RoleModel>> list(@ParameterObject Pageable pageable);

	@Operation(summary = "Retrives a role by ID", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid role ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<RoleModel> findById(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") Long roleId);

	@Operation(summary = "Creates a new role", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Role created with success"),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<RoleModel> insert(
			@Parameter(description = "Representation of a new role") RoleInput roleInputDTO);

	@Operation(summary = "Updates an existing role", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category updated with success"),
			@ApiResponse(responseCode = "400", description = "Invalid role ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<RoleModel> update(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") Long roleId,
			@Parameter(description = "Representation of a role with the new data") RoleInput roleInputDTO);

	@Operation(summary = "Remove an existing role", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Role removed with success"),
			@ApiResponse(responseCode = "400", description = "Invalid role ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> delete(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") Long roleId);

	@Operation(summary = "Returns the role count", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Role count retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid role ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<CountModel> count();
}
