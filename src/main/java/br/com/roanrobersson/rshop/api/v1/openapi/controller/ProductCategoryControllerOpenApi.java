package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Category")
public interface ProductCategoryControllerOpenApi {

	@Operation(summary = "Retrives the product category list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Set<UUID>> findAll(
			@Parameter(description = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);

	@Operation(summary = "Link a category to a product")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Link with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> link(
			@Parameter(description = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			@Parameter(description = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId);

	@Operation(summary = "Unlink a category from a product")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Unlink with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> unlink(
			@Parameter(description = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			@Parameter(description = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId);
}
