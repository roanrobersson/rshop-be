package br.com.roanrobersson.rshop.domain.exception;

import java.util.UUID;

public class PrivilegeNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PrivilegeNotFoundException(String message) {
		super(message);
	}

	public PrivilegeNotFoundException(UUID privilegeId) {
		this(String.format("There is no privilege with the ID %s", privilegeId));
	}
}
