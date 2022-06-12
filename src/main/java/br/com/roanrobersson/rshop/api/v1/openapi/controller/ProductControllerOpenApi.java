package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.dto.model.ProductModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product")
public interface ProductControllerOpenApi {

	@Operation(summary = "Retrives a products page by filters")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Products retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Page<ProductModel>> list(
			@Parameter(description = "Category array", example = "[5c2b2b98-7b72-42dd-8add-9e97a2967e11, 431d856e-caf2-4367-823a-924ce46b2e02]") UUID[] categories,
			@Parameter(description = "Product's name", example = "Computer", required = false) String name,
			@ParameterObject Pageable pageable);

	@Operation(summary = "Retrives a product by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid category ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<ProductModel> findById(
			@Parameter(description = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);

	@Operation(summary = "Creates a new product", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product created with success"),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<ProductModel> insert(
			@Parameter(description = "Representation of a new product") ProductInput productInputDTO);

	@Operation(summary = "Updates an existing product", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product updated with success"),
			@ApiResponse(responseCode = "400", description = "Invalid product ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<ProductModel> update(
			@Parameter(description = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			@Parameter(description = "Representation of a product with the new data") ProductInput productInputDTO);

	@Operation(summary = "Remove an existing product", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Product removed with success"),
			@ApiResponse(responseCode = "400", description = "Invalid category ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> delete(
			@Parameter(description = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);
}
