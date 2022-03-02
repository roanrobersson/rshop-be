package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.CategoryModel;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category")
public interface CategoryControllerOpenApi {

	@Operation(summary = "Retrive a categories page")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Page<CategoryModel>> findAll(
			@Parameter(name = "Page number", example = "3", required = false) Integer page,
			@Parameter(name = "Registers per page", example = "15", required = false) Integer linesPerPage,
			@Parameter(name = "Sort direction", example = "DESC", required = false) String direction,
			@Parameter(name = "Property to orderby", example = "uf", required = false) String orderBy);

	@Operation(summary = "Retrives a category by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrived with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<CategoryModel> findById(
			@Parameter(description = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId);

	@Operation(summary = "Creates a new category")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<CategoryModel> insert(CategoryInput categoryInputDTO);

	@Operation(summary = "Updates an existing category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "422", description = "Unprocessable entity"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<CategoryModel> update(
			@Parameter(name = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId,
			CategoryInput categoryInputDTO);

	@Operation(summary = "Remove an existing category")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Removed with success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "404", description = "Resource not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Void> delete(
			@Parameter(name = "ID of a category", example = "753dad79-2a1f-4f5c-bbd1-317a53587518") UUID categoryId);
}
