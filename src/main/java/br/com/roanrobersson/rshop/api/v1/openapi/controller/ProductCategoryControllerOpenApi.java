package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product Category")
public interface ProductCategoryControllerOpenApi {

	@ApiOperation(value = "Retrives the product category list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Set<UUID>> findAll(
			@ApiParam(value = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);

	@ApiOperation(value = "Link a category to a product")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Link with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> link(
			@ApiParam(value = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			@ApiParam(value = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId);

	@ApiOperation(value = "Unlink a category from a product")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Unlink with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> unlink(
			@ApiParam(value = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			@ApiParam(value = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId);
}
