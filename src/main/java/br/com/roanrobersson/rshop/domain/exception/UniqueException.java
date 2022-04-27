package br.com.roanrobersson.rshop.domain.exception;

public class UniqueException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public UniqueException(String message) {
		super(message);
	}

	public UniqueException(String message, Throwable reason) {
		super(message, reason);
	}
}
