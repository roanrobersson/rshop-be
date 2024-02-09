package br.com.roanrobersson.rshop.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Long productId) {
		this(String.format("There is no product with the ID %s", productId));
	}
}
