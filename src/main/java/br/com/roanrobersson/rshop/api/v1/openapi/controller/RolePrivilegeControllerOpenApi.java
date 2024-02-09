package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Role Privilege")
public interface RolePrivilegeControllerOpenApi {

	@Operation(summary = "Retrives the role privilege list", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Privileges retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Set<Long>> list(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") Long roleId);

	@Operation(summary = "Link a category to a role", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Link with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> link(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") Long roleId,
			@Parameter(description = "ID of a privilege", example = "585a242f-1058-476c-8656-eafe1fed5812") Long privilegeId);

	@Operation(summary = "Unlink a privilege from a role", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Unlink with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> unlink(
			@Parameter(description = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") Long roleId,
			@Parameter(description = "ID of a privilege", example = "585a242f-1058-476c-8656-eafe1fed5812") Long privilegeId);
}
