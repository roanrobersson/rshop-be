package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.exception.Problem;
import br.com.roanrobersson.rshop.domain.dto.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.dto.model.CategoryModel;
import br.com.roanrobersson.rshop.domain.dto.model.CountModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category")
public interface CategoryControllerOpenApi {
	@Operation(summary = "Retrive a categories page", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categories retrived with success"),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Page<CategoryModel>> list(@ParameterObject Pageable pageable);

	@Operation(summary = "Retrives a category by ID", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid category ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<CategoryModel> findById(
			@Parameter(description = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") Long categoryId);

	@Operation(summary = "Creates a new category", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Category created with success"),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<CategoryModel> insert(
			@Parameter(description = "Representation of a new category") CategoryInput categoryInputDTO);

	@Operation(summary = "Updates an existing category", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category updated with success"),
			@ApiResponse(responseCode = "400", description = "Invalid category ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<CategoryModel> update(
			@Parameter(description = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") Long categoryId,
			@Parameter(description = "Representation of a category with the new data") CategoryInput categoryInputDTO);

	@Operation(summary = "Remove an existing category", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Category removed with success"),
			@ApiResponse(responseCode = "400", description = "Invalid category ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<Void> delete(
			@Parameter(description = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") Long categoryId);

	@Operation(summary = "Returns the category count", security = @SecurityRequirement(name = "OAuth2"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Category count retrived with success"),
			@ApiResponse(responseCode = "400", description = "Invalid category ID", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Problem.class))) })
	public ResponseEntity<CountModel> count();
}
