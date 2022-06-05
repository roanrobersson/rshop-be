package br.com.roanrobersson.rshop.builder;

import java.time.Instant;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.mapper.PrivilegeMapper;
import br.com.roanrobersson.rshop.api.v1.model.PrivilegeModel;
import br.com.roanrobersson.rshop.api.v1.model.input.PrivilegeInput;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import lombok.Getter;

@Getter
public class PrivilegeBuilder {

	public static final UUID EXISTING_ID = UUID.fromString("b7705487-51a1-4092-8b62-91dccd76a41a");
	public static final UUID ANOTHER_EXISTING_ID = UUID.fromString("91f550d9-548f-4d09-ac9c-1a95219033f7");
	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-4000-0000-000000000000");
	public static final UUID DEPENDENT_ID = UUID.fromString("b7705487-51a1-4092-8b62-91dccd76a41a");
	public static final String NON_EXISTING_NAME = "NON_EXISTING_PRIVILEGE_NAME";
	public static final String EXISTING_NAME = "CONSULT_CATEGORIES";
	public static final String ANOTHER_EXISTING_NAME = "EDIT_ADDRESSES";
	public static final Instant VALID_INSTANT = Instant.parse("2020-10-20T03:00:00Z");
	public static final String VALID_DESCRIPTION = "Description";

	private UUID id = EXISTING_ID;
	private String name = EXISTING_NAME;

	public static PrivilegeBuilder aPrivilege() {
		return new PrivilegeBuilder();
	}

	public static PrivilegeBuilder anExistingPrivilege() {
		PrivilegeBuilder builder = new PrivilegeBuilder();
		builder.id = EXISTING_ID;
		builder.name = EXISTING_NAME;
		return builder;
	}

	public static PrivilegeBuilder aNonExistingPrivilege() {
		PrivilegeBuilder builder = new PrivilegeBuilder();
		builder.id = NON_EXISTING_ID;
		builder.name = NON_EXISTING_NAME;
		return builder;
	}

	public PrivilegeBuilder withId(UUID id) {
		this.id = id;
		return this;
	}

	public PrivilegeBuilder withExistingId() {
		this.id = EXISTING_ID;
		return this;
	}

	public PrivilegeBuilder withAnotherExistingId() {
		this.id = ANOTHER_EXISTING_ID;
		return this;
	}

	public PrivilegeBuilder withNonExistingId() {
		this.id = NON_EXISTING_ID;
		return this;
	}

	public PrivilegeBuilder withDependentId() {
		this.id = DEPENDENT_ID;
		return this;
	}

	public PrivilegeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public PrivilegeBuilder withNonExistingName() {
		this.name = NON_EXISTING_NAME;
		return this;
	}

	public PrivilegeBuilder withExistingName() {
		this.name = EXISTING_NAME;
		return this;
	}

	public PrivilegeBuilder withAnotherExistingName() {
		this.name = ANOTHER_EXISTING_NAME;
		return this;
	}

	public Privilege build() {
		return new Privilege(id, name, VALID_DESCRIPTION, VALID_INSTANT, VALID_INSTANT);
	}

	public PrivilegeInput buildInput() {
		return PrivilegeMapper.INSTANCE.toInput(build());
	}

	public PrivilegeModel buildModel() {
		return PrivilegeMapper.INSTANCE.toModel(build());
	}
};