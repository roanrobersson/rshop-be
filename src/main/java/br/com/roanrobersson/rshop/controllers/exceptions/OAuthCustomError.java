package br.com.roanrobersson.rshop.controllers.exceptions;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthCustomError implements Serializable {
	private static final long serialVersionUID = 1L;

	private String error;

	@JsonProperty("error_description")
	private String errorDescription;

	OAuthCustomError() {
	}

	public OAuthCustomError(String error, String errorDescription) {
		super();
		this.error = error;
		this.errorDescription = errorDescription;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
