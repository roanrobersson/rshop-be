package br.com.roanrobersson.rshop.core.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.api.exception.FieldMessage;
import br.com.roanrobersson.rshop.api.v1.dto.input.CategoryInputDTO;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;

public class CategoryInputValidator implements ConstraintValidator<CategoryInputValid, CategoryInputDTO> {

	private static final String MSG_CATEGORY_ALREADY_EXISTS = "Category already exists";

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CategoryRepository repository;

	@Override
	public void initialize(CategoryInputValid ann) {
	}

	@Override
	public boolean isValid(CategoryInputDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("categoryId");

		List<FieldMessage> list = new ArrayList<>();

		Optional<Category> optional = repository.findByName(dto.getName());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("name", MSG_CATEGORY_ALREADY_EXISTS));
		}

		// Update
		if (isUpdateRequest) {
			long categoryId = Long.parseLong(uriVars.get("categoryId"));
			if (categoryId != optional.get().getId()) {
				list.add(new FieldMessage("name", MSG_CATEGORY_ALREADY_EXISTS));
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