package br.com.roanrobersson.rshop.core.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.api.exception.FieldMessage;
import br.com.roanrobersson.rshop.api.v1.dto.input.AddressInputDTO;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.repository.AddressRepository;

public class AddressInputValidator implements ConstraintValidator<AddressInputValid, AddressInputDTO> {

	private static final String MSG_ADDRESS_ALREADY_EXISTS = "Address is already exists";

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AddressRepository repository;

	@Override
	public void initialize(AddressInputValid ann) {
	}

	@Override
	public boolean isValid(AddressInputDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("addressId");

		List<FieldMessage> list = new ArrayList<>();

		UUID userId = UUID.fromString(uriVars.get("userId"));
		Optional<Address> optional = repository.findByUserIdAndNick(userId, dto.getNick());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("nick", MSG_ADDRESS_ALREADY_EXISTS));
		}

		// Update
		if (isUpdateRequest) {
			if (uriVars.get("addressId") != optional.get().getId().toString()) {
				list.add(new FieldMessage("nick", MSG_ADDRESS_ALREADY_EXISTS));
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