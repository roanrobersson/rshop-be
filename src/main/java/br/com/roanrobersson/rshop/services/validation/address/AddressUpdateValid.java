package br.com.roanrobersson.rshop.services.validation.address;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AddressUpdateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface AddressUpdateValid {
	String message() default "Validation error";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
