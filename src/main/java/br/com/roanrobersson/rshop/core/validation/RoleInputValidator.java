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
import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;

public class RoleInputValidator implements ConstraintValidator<RoleInputValid, RoleInputDTO> {

	private static final String MSG_ROLE_ALREADY_EXISTS = "Role is already exists";

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RoleRepository repository;

	@Override
	public void initialize(RoleInputValid ann) {
	}

	@Override
	public boolean isValid(RoleInputDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("roleId");

		List<FieldMessage> list = new ArrayList<>();

		Optional<Role> optional = repository.findByName(dto.getName());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("name", MSG_ROLE_ALREADY_EXISTS));
		}

		// Update
		if (isUpdateRequest) {
			if (uriVars.get("roleId") != optional.get().getId().toString()) {
				list.add(new FieldMessage("name", MSG_ROLE_ALREADY_EXISTS));
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