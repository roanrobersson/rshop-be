package br.com.roanrobersson.rshop.services.validation.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.product.ProductUpdateDTO;
import br.com.roanrobersson.rshop.entities.Product;
import br.com.roanrobersson.rshop.repositories.ProductRepository;

public class ProductUpdateValidator implements ConstraintValidator<ProductUpdateValid, ProductUpdateDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ProductRepository repository;
	
	@Override
	public void initialize(ProductUpdateValid ann) {
	}

	@Override
	public boolean isValid(ProductUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long categoryId = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Product product = repository.findByName(dto.getName());
		
		System.out.println(product);

		if (product != null && categoryId != product.getId()) {
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