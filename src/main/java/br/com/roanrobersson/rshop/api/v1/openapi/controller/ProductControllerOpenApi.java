package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product")
public interface ProductControllerOpenApi {

	@Operation(summary = "Retrives a products page by filters")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Page<ProductModel>> findAll(
			@Parameter(name = "Category array", example = "[5c2b2b98-7b72-42dd-8add-9e97a2967e11, 431d856e-caf2-4367-823a-924ce46b2e02]") UUID[] categories,
			@Parameter(name = "Product's name", example = "uf", required = false) String name,
			@Parameter(name = "Page number", example = "3", required = false) Integer page,
			@Parameter(name = "Register per page", example = "15", required = false) Integer linesPerPage,
			@Parameter(name = "Sort direction", example = "DESC", required = false) String direction,
			@Parameter(name = "Property to orderby", example = "uf", required = false) String orderBy);

	@Operation(summary = "Retrives a product by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ProductModel> findById(
			@Parameter(name = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);

	@Operation(summary = "Creates a new product")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ProductModel> insert(ProductInput productInputDTO);

	@Operation(summary = "Updates an existing product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ProductModel> update(
			@Parameter(name = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			ProductInput productInputDTO);

	@Operation(summary = "Removes an existing product")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Removed with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> delete(
			@Parameter(name = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);
}
