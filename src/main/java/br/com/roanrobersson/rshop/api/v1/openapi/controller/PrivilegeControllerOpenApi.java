package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.roanrobersson.rshop.api.v1.dto.PrivilegeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Privilege")
public interface PrivilegeControllerOpenApi {

	@ApiOperation(value = "Retrives the privileges list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<List<PrivilegeDTO>> getPrivileges(
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy);
}
