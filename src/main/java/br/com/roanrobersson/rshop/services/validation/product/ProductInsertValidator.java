package br.com.roanrobersson.rshop.services.validation.product;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.product.ProductInsertDTO;
import br.com.roanrobersson.rshop.entities.Product;
import br.com.roanrobersson.rshop.repositories.ProductRepository;

public class ProductInsertValidator implements ConstraintValidator<ProductInsertValid, ProductInsertDTO> {
	
	@Autowired
	private ProductRepository repository;
	
	@Override
	public void initialize(ProductInsertValid ann) {
	}

	@Override
	public boolean isValid(ProductInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Product product = repository.findByName(dto.getName());

		if (product != null) {
			list.add(new FieldMessage("name", "Product already exists"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}