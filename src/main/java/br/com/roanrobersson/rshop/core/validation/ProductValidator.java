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
import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.domain.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;

public class ProductValidator implements ConstraintValidator<ProductValid, ProductDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ProductRepository repository;

	@Override
	public void initialize(ProductValid ann) {
	}

	@Override
	public boolean isValid(ProductDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("productId");

		List<FieldMessage> list = new ArrayList<>();

		Optional<Product> optional = repository.findByName(dto.getName());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("name", "Product already exists"));
		}

		// Update
		if (isUpdateRequest) {
			Long productId = Long.parseLong(uriVars.get("productId"));
			if (productId != optional.get().getId()) {
				list.add(new FieldMessage("name", "Product already exists"));
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