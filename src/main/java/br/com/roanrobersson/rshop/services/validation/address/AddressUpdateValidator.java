package br.com.roanrobersson.rshop.services.validation.address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.address.AddressUpdateDTO;
import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.repositories.AddressRepository;

public class AddressUpdateValidator implements ConstraintValidator<AddressUpdateValid, AddressUpdateDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AddressRepository repository;
	
	@Override
	public void initialize(AddressUpdateValid ann) {
	}

	@Override
	public boolean isValid(AddressUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long addressId = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Address address = repository.findByNick(dto.getNick());
		
		if (address != null && addressId != address.getId()) {
			list.add(new FieldMessage("nick", "Address is already exists"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}