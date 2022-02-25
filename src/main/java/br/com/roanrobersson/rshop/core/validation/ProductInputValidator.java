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
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;

public class ProductInputValidator implements ConstraintValidator<ProductInputValid, ProductInput> {

	private static final String MSG_PRODUCT_ALREADY_EXISTS = "Product already exists";

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ProductRepository repository;

	@Override
	public void initialize(ProductInputValid ann) {
	}

	@Override
	public boolean isValid(ProductInput dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("productId");

		List<FieldMessage> list = new ArrayList<>();

		Optional<Product> optional = repository.findByName(dto.getName());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("name", MSG_PRODUCT_ALREADY_EXISTS));
		}

		// Update
		if (isUpdateRequest) {
			if (uriVars.get("productId") != optional.get().getId().toString()) {
				list.add(new FieldMessage("name", MSG_PRODUCT_ALREADY_EXISTS));
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