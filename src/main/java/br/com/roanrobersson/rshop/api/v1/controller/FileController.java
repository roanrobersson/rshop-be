package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.api.v1.dto.UriDTO;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/files")
@Api(tags = "File")
public class FileController {

	@Autowired
	private FileService service;

	@PostMapping
	@CheckSecurity.File.CanEdit
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Uploads a new file")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Uploaded with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 415, message = "Unsupported media type"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<UriDTO> insert(@RequestParam("file") MultipartFile file) {
		URL url = service.insert(file);
		UriDTO uriDto = new UriDTO(url.toString());
		return ResponseEntity.ok().body(uriDto);
	}
}
