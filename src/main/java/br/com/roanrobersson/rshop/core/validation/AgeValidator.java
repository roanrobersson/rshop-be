package br.com.roanrobersson.rshop.core.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {

	private int minAge;
	private int maxAge;

	@Override
	public void initialize(Age ann) {
		this.minAge = ann.min();
		this.maxAge = ann.max();
	}

	@Override
	public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {

		int age = Period.between(birthDate, LocalDate.now()).getYears();

		if (age < minAge || age > maxAge) {
			return false;
		}

		return true;
	}
}