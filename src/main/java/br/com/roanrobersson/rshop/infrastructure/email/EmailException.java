package br.com.roanrobersson.rshop.infrastructure.email;

public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public EmailException(String msg) {
		super(msg);
	}
}
