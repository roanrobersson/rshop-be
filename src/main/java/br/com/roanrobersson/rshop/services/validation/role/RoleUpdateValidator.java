package br.com.roanrobersson.rshop.services.validation.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.role.RoleUpdateDTO;
import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.repositories.RoleRepository;

public class RoleUpdateValidator implements ConstraintValidator<RoleUpdateValid, RoleUpdateDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private RoleRepository repository;
	
	@Override
	public void initialize(RoleUpdateValid ann) {
	}

	@Override
	public boolean isValid(RoleUpdateDTO dto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String roleId = uriVars.get("id");
		
		List<FieldMessage> list = new ArrayList<>();
		
		Role role = repository.findByAuthority(dto.getAuthority());

		if (role != null && !roleId.equals(role.getId())) {
			list.add(new FieldMessage("authority", "Role already exists"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}