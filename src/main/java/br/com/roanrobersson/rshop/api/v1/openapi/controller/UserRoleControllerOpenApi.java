package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "User Role")
public interface UserRoleControllerOpenApi {

	@ApiOperation(value = "Retrives the user roles list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Set<UUID>> findAll(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId);

	@ApiOperation(value = "Grants a role to a user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Granted with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> grant(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@ApiParam(value = "ID of a role", example = "18aace1e-f36a-4d71-b4d1-124387d9b63a") UUID roleId);

	@ApiOperation(value = "Revokes a role of a user")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Revoked with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> revoke(
			@ApiParam(value = "ID of a user", example = "821e3c67-7f22-46af-978c-b6269cb15387") UUID userId,
			@ApiParam(value = "ID of a user role", example = "18aace1e-f36a-4d71-b4d1-124387d9b63a") UUID roleId);
}
