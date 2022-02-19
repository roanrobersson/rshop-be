package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.api.v1.dto.UriDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "File")
public interface FileControllerOpenApi {

	@ApiOperation(value = "Uploads a new file")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Uploaded with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 415, message = "Unsupported media type"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<UriDTO> insert(MultipartFile file);
}
