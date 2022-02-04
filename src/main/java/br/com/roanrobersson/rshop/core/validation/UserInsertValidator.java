package br.com.roanrobersson.rshop.core.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.api.exception.FieldMessage;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

	@Autowired
	private UserRepository repository;

	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Optional<User> optional = repository.findByEmail(dto.getEmail());

		if (optional.isPresent()) {
			list.add(new FieldMessage("email", "Email is already in use"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}