package br.com.roanrobersson.rshop.services.validation.category;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.category.CategoryInsertDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;

public class CategoryInsertValidator implements ConstraintValidator<CategoryInsertValid, CategoryInsertDTO> {
	
	@Autowired
	private CategoryRepository repository;
	
	@Override
	public void initialize(CategoryInsertValid ann) {
	}

	@Override
	public boolean isValid(CategoryInsertDTO dto, ConstraintValidatorContext context) {
		
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