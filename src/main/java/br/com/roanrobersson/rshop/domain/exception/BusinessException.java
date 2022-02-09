package br.com.roanrobersson.rshop.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable reason) {
		super(message, reason);
	}
}
