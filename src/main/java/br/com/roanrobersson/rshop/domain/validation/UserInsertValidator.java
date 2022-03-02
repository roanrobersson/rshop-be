package br.com.roanrobersson.rshop.domain.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.api.exception.FieldMessage;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsert> {

	private static final String MSG_EMAIL_IN_USE = "Email is already in use";

	@Autowired
	private UserRepository repository;

	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsert dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		Optional<User> optional = repository.findByEmail(dto.getEmail());

		if (optional.isPresent()) {
			list.add(new FieldMessage("email", MSG_EMAIL_IN_USE));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}