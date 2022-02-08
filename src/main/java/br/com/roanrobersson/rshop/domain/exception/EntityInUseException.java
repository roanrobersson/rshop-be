package br.com.roanrobersson.rshop.domain.exception;

public class EntityInUseException extends DatabaseException {

	private static final long serialVersionUID = 1L;

	public EntityInUseException(String message) {
		super(message);
	}

}
