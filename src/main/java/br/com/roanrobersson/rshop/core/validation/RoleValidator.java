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
import br.com.roanrobersson.rshop.api.v1.dto.RoleDTO;
import br.com.roanrobersson.rshop.domain.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;

public class RoleValidator implements ConstraintValidator<RoleValid, RoleDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RoleRepository repository;

	@Override
	public void initialize(RoleValid ann) {
	}

	@Override
	public boolean isValid(RoleDTO dto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isUpdateRequest = uriVars.containsKey("roleId");

		List<FieldMessage> list = new ArrayList<>();

		Optional<Role> optional = repository.findByName(dto.getName());

		if (optional.isEmpty())
			return true;

		// Insert
		if (!isUpdateRequest) {
			list.add(new FieldMessage("name", "Role already exists"));
		}

		// Update
		if (isUpdateRequest) {
			Long roleId = Long.valueOf(uriVars.get("roleId"));
			if (roleId != optional.get().getId()) {
				list.add(new FieldMessage("name", "Role already exists"));
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