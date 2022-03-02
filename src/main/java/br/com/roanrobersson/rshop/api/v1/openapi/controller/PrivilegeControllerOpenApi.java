package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.PrivilegeModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Privilege")
public interface PrivilegeControllerOpenApi {

	@Operation(summary = "Retrives the privileges list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<PrivilegeModel>> getPrivileges(
			@Parameter(description = "Sort direction", example = "DESC", required = false) String direction,
			@Parameter(description = "Property to orderby", example = "name", required = false) String orderBy);
}
