package br.com.roanrobersson.rshop.domain.exception;

import java.util.UUID;

public class ProductNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(UUID productId) {
		this(String.format("There is no product with the ID %s", productId));
	}
}
