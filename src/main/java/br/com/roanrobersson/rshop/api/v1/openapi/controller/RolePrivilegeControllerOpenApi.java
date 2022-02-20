package br.com.roanrobersson.rshop.api.v1.openapi.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Role Privilege")
public interface RolePrivilegeControllerOpenApi {

	@ApiOperation(value = "Retrives the role privileges list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Set<UUID>> findAll(
			@ApiParam(value = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId);

	@ApiOperation(value = "Link a privilege to a role")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Link with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> link(
			@ApiParam(value = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId,
			@ApiParam(value = "ID of a privilege", example = "585a242f-1058-476c-8656-eafe1fed5812") UUID privilegeId);

	@ApiOperation(value = "Unlink a privilege from a role")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Unlink with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<Void> unlink(
			@ApiParam(value = "ID of a role", example = "5e0b121c-9f12-4fd3-a7e6-179b5007149a") UUID roleId,
			@ApiParam(value = "ID of a privilege", example = "585a242f-1058-476c-8656-eafe1fed5812") UUID privilegeId);
}
