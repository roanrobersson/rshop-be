package br.com.roanrobersson.rshop.api.exception;

import lombok.Getter;

@Getter
public enum ProblemType {

	INVALID_DATA("/invalid-data", "Invalid data"),
	ACCESS_DENIED("/access-denied", "Access denied"),
	SYSTEM_ERROR("/system-error", "System error"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
	INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible message"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	ENTITY_IN_USE("/entity-in-use", "Entity in use"),
	BUSINESS_ERROR("/business-error", "Business rule violation");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://rshop.com" + path;
		this.title = title;
	}
	
}
