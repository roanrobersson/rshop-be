package br.com.roanrobersson.rshop.domain.exception;

import java.util.UUID;

public class CategoryNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(String message) {
		super(message);
	}

	public CategoryNotFoundException(UUID categoryId) {
		this(String.format("There is no category with the ID %s", categoryId));
	}
}
