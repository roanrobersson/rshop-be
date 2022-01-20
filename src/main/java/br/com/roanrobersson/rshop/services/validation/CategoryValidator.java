package br.com.roanrobersson.rshop.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.domain.dto.CategoryDTO;
import br.com.roanrobersson.rshop.domain.entities.Category;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;

public class CategoryValidator implements ConstraintValidator<CategoryValid, CategoryDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CategoryRepository repository;

	@Override
	public void initialize(CategoryValid ann) {
	}

	@Override
	public boolean isValid(CategoryDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("categoryId");

		List<FieldMessage> list = new ArrayList<>();

		Optional<Category> optional = repository.findByName(dto.getName());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("name", "Category already exists"));
		}

		// Update
		if (isUpdateRequest) {
			long categoryId = Long.parseLong(uriVars.get("categoryId"));
			if (categoryId != optional.get().getId()) {
				list.add(new FieldMessage("name", "Category already exists"));
			}
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}