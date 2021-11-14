package br.com.roanrobersson.rshop.services.validation.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.category.CategoryUpdateDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;

public class CategoryUpdateValidator implements ConstraintValidator<CategoryUpdateValid, CategoryUpdateDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CategoryRepository repository;
	
	@Override
	public void initialize(CategoryUpdateValid ann) {
	}

	@Override
	public boolean isValid(CategoryUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long categoryId = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Category category = repository.findByName(dto.getName());
		
		System.out.println(category);

		if (category != null && categoryId != category.getId()) {
			list.add(new FieldMessage("name", "Categoria já existe"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}