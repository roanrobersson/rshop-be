package br.com.roanrobersson.rshop.domain.dto.input.id.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.roanrobersson.rshop.domain.dto.input.id.CategoryIdInput;

public class PrivilegeIdInputSerializer extends StdSerializer<CategoryIdInput> {

	private static final long serialVersionUID = 1L;

	public PrivilegeIdInputSerializer() {
		this(null);
	}

	public PrivilegeIdInputSerializer(Class<CategoryIdInput> t) {
		super(t);
	}

	@Override
	public void serialize(CategoryIdInput value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		jgen.writeString(value.getId());
	}
}