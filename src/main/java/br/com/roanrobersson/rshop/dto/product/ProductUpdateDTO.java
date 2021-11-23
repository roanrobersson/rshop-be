package br.com.roanrobersson.rshop.dto.product;

import java.io.Serializable;
import java.time.Instant;

import br.com.roanrobersson.rshop.entities.Product;
import br.com.roanrobersson.rshop.services.validation.product.ProductUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ProductUpdateValid
@Getter @Setter @NoArgsConstructor
public class ProductUpdateDTO extends AbstractProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public ProductUpdateDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		super(id, name, description, price, imgUrl, date);
	}
	
	public ProductUpdateDTO(Product entity) {
		super(entity);
	}
}
