package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.api.v1.model.UriModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File")
public interface FileControllerOpenApi {

	@Operation(summary = "Uploads a new file")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Uploaded with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "415", description = "Unsupported media type"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<UriModel> insert(MultipartFile file);
}
