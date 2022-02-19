package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
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
	public ResponseEntity<Page<ProductDTO>> findAll(
			@ApiParam(value = "Category array", example = "[5b79649cf311465fb2d907355b56d08a, 32de2852e42f4b1497bec60f2e9098f4]") UUID[] categories,
			@ApiParam(value = "Product's name", example = "uf", required = false) String name,
			@ApiParam(value = "Page number", example = "3", required = false) Integer page,
			@ApiParam(value = "Register per page", example = "15", required = false) Integer linesPerPage,
			@ApiParam(value = "Sort direction", example = "DESC", required = false) String direction,
			@ApiParam(value = "Property to orderby", example = "uf", required = false) String orderBy);

	@ApiOperation(value = "Retrives a category by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ProductDTO> findById(
			@ApiParam(value = "ID of a product", example = "5b79649cf311465fb2d907355b56d08a") UUID productId);

	@ApiOperation(value = "Creates a new product")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ProductDTO> insert(ProductInputDTO productInputDTO);

	@ApiOperation(value = "Updates an existing category")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ProductDTO> update(
			@ApiParam(value = "ID of a product", example = "5b79649cf311465fb2d907355b56d08a") UUID productId,
			ProductInputDTO productInputDTO);

	@ApiOperation(value = "Removes an existing product")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(
			@ApiParam(value = "ID of a product", example = "5b79649cf311465fb2d907355b56d08a") UUID productId);
}