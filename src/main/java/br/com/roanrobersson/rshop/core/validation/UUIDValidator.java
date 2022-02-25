package br.com.roanrobersson.rshop.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<UUIDValid, String> {
	private final String regex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

	@Override
	public void initialize(UUIDValid UUIDValid) {
	}

	@Override
	public boolean isValid(String uuid, ConstraintValidatorContext cxt) {
		if (uuid == null) {
			return true;
		}
		return uuid.toString().matches(this.regex);
	}
}