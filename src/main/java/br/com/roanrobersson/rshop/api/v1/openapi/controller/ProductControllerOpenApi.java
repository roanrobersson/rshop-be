package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Product")
public interface ProductControllerOpenApi {

	@ApiOperation(value = "Retrives a products page by filters")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Page<ProductModel>> findAll(
			@ApiParam(value = "Category array", example = "[5c2b2b98-7b72-42dd-8add-9e97a2967e11, 431d856e-caf2-4367-823a-924ce46b2e02]") UUID[] categories,
			@ApiParam(value = "Product's name", example = "uf", required = false) String name,
			@ApiParam(value = "Page number", example = "3", required = false) Integer page,
			@ApiParam(value = "Register per page", example = "15", required = false) Integer linesPerPage,
			@ApiParam(value = "Sort direction", example = "DESC", required = false) String direction,
			@ApiParam(value = "Property to orderby", example = "uf", required = false) String orderBy);

	@ApiOperation(value = "Retrives a product by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ProductModel> findById(
			@ApiParam(value = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);

	@ApiOperation(value = "Creates a new product")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ProductModel> insert(ProductInput productInputDTO);

	@ApiOperation(value = "Updates an existing product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ProductModel> update(
			@ApiParam(value = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId,
			ProductInput productInputDTO);

	@ApiOperation(value = "Removes an existing product")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(
			@ApiParam(value = "ID of a product", example = "7c4125cc-8116-4f11-8fc3-f40a0775aec7") UUID productId);
}
