package br.com.roanrobersson.rshop.core.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeValid, LocalDate> {

	private int minAge;
	private int maxAge;

	@Override
	public void initialize(AgeValid ann) {
		this.minAge = ann.min();
		this.maxAge = ann.max();
	}

	@Override
	public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
		if (birthDate == null) {
			return true;
		}
		
		int age = Period.between(birthDate, LocalDate.now()).getYears();

		if (age < minAge || age > maxAge) {
			return false;
		}

		return true;
	}
}