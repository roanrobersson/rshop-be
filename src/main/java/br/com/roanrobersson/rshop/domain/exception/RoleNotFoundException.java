package br.com.roanrobersson.rshop.domain.exception;

import java.util.UUID;

public class RoleNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(String message) {
		super(message);
	}

	public RoleNotFoundException(UUID roleId) {
		this(String.format("There is no role with the ID %s", roleId));
	}
}
