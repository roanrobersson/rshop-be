package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.api.v1.model.UriModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ImageFileInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File")
public interface FileControllerOpenApi {

	@Operation(summary = "Uploads a new image file", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Image file uploaded with success"),
			@ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<UriModel> insertImage(
			@Parameter(description = "Representation of a new image file") ImageFileInput file);
}
