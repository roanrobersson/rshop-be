package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.roanrobersson.rshop.api.v1.model.UserModel;
import br.com.roanrobersson.rshop.api.v1.model.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "User")
public interface UserControllerOpenApi {

	@ApiOperation(value = "Retrives a users page")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Page<UserModel>> findAll(
			@ApiParam(value = "Page number", example = "3", required = false) Integer page,
			@ApiParam(value = "Register per page", example = "15", required = false) Integer linesPerPage,
			@ApiParam(value = "Sort direction", example = "DESC", required = false) String direction,
			@ApiParam(value = "Property to orderby", example = "uf", required = false) String orderBy);

	@ApiOperation(value = "Retrives a user by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<UserModel> findById(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@ApiOperation(value = "Creates a new user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<UserModel> insert(UserInsert userInsertDTO);

	@ApiOperation(value = "Updates an existing user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<UserModel> update(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			UserUpdate userUpdateDTO);

	@ApiOperation(value = "Removes an existing user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> delete(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@ApiOperation(value = "Changes a user's password")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Changed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> changePassword(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			UserChangePasswordInput userChangePasswordDTO);
}
