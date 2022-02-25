package br.com.roanrobersson.rshop.core.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import br.com.roanrobersson.rshop.core.validation.AgeValid.List;

/**
 * The value of the annotated element must be within the specified age limits,
 * based on the birth date and the current date.
 * <p>
 * Accepts {@code LocalDate}</li>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Roan Robersson de Oliveira
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Constraint(validatedBy = AgeValidator.class)
public @interface AgeValid {

	String message() default "Must have at least {min} and less than {max} years old";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int min() default 1;

	int max() default Integer.MAX_VALUE;

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		AgeValid[] value();
	}
}