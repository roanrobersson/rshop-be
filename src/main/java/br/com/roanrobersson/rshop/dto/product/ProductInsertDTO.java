package br.com.roanrobersson.rshop.dto.product;

import java.io.Serializable;
import java.time.Instant;

import br.com.roanrobersson.rshop.entities.Product;
import br.com.roanrobersson.rshop.services.validation.product.ProductInsertValid;

@ProductInsertValid
public class ProductInsertDTO extends AbstractProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public ProductInsertDTO() {
		super();
	}

	public ProductInsertDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		super(id, name, description, price, imgUrl, date);
	}
	
	public ProductInsertDTO(Product entity) {
		super(entity);
	}
}