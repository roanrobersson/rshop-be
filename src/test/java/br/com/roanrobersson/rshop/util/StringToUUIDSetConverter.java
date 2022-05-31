package br.com.roanrobersson.rshop.util;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public final class StringToUUIDSetConverter extends SimpleArgumentConverter {

	@Override
	protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
		if (source instanceof String && Set.class.isAssignableFrom(targetType)) {
			return Set.of(((String) source).split("\\s*,\\s*")).stream().filter(s -> !s.isBlank())
					.map(s -> UUID.fromString(s)).collect(Collectors.toSet());
		} else {
			throw new IllegalArgumentException(
					"Conversion from " + source.getClass() + " to " + targetType + " not supported.");
		}
	}

}
