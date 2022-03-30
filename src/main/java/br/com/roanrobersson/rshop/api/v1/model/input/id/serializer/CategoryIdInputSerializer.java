package br.com.roanrobersson.rshop.api.v1.model.input.id.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.roanrobersson.rshop.api.v1.model.input.id.CategoryIdInput;

public class CategoryIdInputSerializer extends StdSerializer<CategoryIdInput> {

	private static final long serialVersionUID = 1L;

	public CategoryIdInputSerializer() {
		this(null);
	}

	public CategoryIdInputSerializer(Class<CategoryIdInput> t) {
		super(t);
	}

	@Override
	public void serialize(CategoryIdInput value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		jgen.writeString(value.getId());
	}
}