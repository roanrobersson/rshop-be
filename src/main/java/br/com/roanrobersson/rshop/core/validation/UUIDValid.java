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

import br.com.roanrobersson.rshop.core.validation.UUIDValid.List;

/**
 * The annotated {@code CharSequence} must match the hexadecimal pattern:
 * FFFFFFFF-FFFF-4FFF-FFFF-FFFFFFFFFFFF (Only UUID v3 and v4 are valid)
 * <p>
 * Accepts {@code CharSequence}</li>
 * <p>
 * {@code null} elements are considered valid.
 * 
 * @author Roan Robersson de Oliveira
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Constraint(validatedBy = { UUIDValidator.class })
public @interface UUIDValid {

	String message() default "Must match the hexadecimal pattern: FFFFFFFF-FFFF-4FFF-FFFF-FFFFFFFFFFFF (Only UUID v3 and v4 are valid)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		UUIDValid[] value();
	}
}