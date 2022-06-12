package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.domain.dto.model.PrivilegeModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Privilege")
public interface PrivilegeControllerOpenApi {

	@Operation(summary = "Retrives the privileges list", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Privileges retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Page<PrivilegeModel>> list(@ParameterObject Pageable pageable);

	@Operation(summary = "Retrives a privilege by ID", security = @SecurityRequirement(name = "basicAuth"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Privilege retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid privilege ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Privilege not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<PrivilegeModel> findById(
			@Parameter(description = "Id of a privilege", example = "b7e8b3c9-d426-42f0-8594-5c46cd112aae", required = false) UUID id);
}
