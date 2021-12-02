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
import br.com.roanrobersson.rshop.dto.AddressDTO;
import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.repositories.AddressRepository;

public class AddressValidator implements ConstraintValidator<AddressValid, AddressDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AddressRepository repository;

	@Override
	public void initialize(AddressValid ann) {
	}

	@Override
	public boolean isValid(AddressDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("addressId");
		
		List<FieldMessage> list = new ArrayList<>();

		Long userId = Long.parseLong(uriVars.get("userId"));
		Optional<Address> optional = repository.findByUserIdAndNick(userId, dto.getNick());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("nick", "Address is already exists"));
		}

		// Update
		if (isUpdateRequest) {
			Long addressId = Long.parseLong(uriVars.get("addressId"));
			if (addressId != optional.get().getId()) {
				list.add(new FieldMessage("nick", "Address is already exists"));
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