package br.com.roanrobersson.rshop.services.validation.product;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.product.ProductInsertDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;

public class ProductInsertValidator implements ConstraintValidator<ProductInsertValid, ProductInsertDTO> {
	
	@Autowired
	private CategoryRepository repository;
	
	@Override
	public void initialize(ProductInsertValid ann) {
	}

	@Override
	public boolean isValid(ProductInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Category category = repository.findByName(dto.getName());

		if (category != null) {
			list.add(new FieldMessage("name", "Category already exists"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}