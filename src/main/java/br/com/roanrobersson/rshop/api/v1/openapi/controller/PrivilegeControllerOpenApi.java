package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.PrivilegeModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Privilege")
public interface PrivilegeControllerOpenApi {

	@ApiOperation(value = "Retrives the privileges list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<List<PrivilegeModel>> getPrivileges(
			@ApiParam(value = "Sort direction", example = "DESC", required = false) String direction,
			@ApiParam(value = "Property to orderby", example = "name", required = false) String orderBy);
}
