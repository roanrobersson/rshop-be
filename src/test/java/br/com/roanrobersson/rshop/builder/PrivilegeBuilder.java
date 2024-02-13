package br.com.roanrobersson.rshop.builder;

import java.time.OffsetDateTime;

import br.com.roanrobersson.rshop.domain.dto.input.PrivilegeInput;
import br.com.roanrobersson.rshop.domain.dto.model.PrivilegeModel;
import br.com.roanrobersson.rshop.domain.mapper.PrivilegeMapper;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import lombok.Getter;

@Getter
public class PrivilegeBuilder {

	public static final Long EXISTING_ID = 1L;
	public static final Long ANOTHER_EXISTING_ID = 2L;
	public static final Long NON_EXISTING_ID = 999999L;
	public static final Long DEPENDENT_ID = 1L;
	public static final String NON_EXISTING_NAME = "NON_EXISTING_PRIVILEGE_NAME";
	public static final String EXISTING_NAME = "CONSULT_CATEGORIES";
	public static final String ANOTHER_EXISTING_NAME = "EDIT_ADDRESSES";
	public static final OffsetDateTime VALID_DATETIME = OffsetDateTime.parse("2020-10-20T03:00:00Z");
	public static final String VALID_DESCRIPTION = "Description";

	private Long id = EXISTING_ID;
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

	public PrivilegeBuilder withId(Long id) {
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
		Privilege privilege = new Privilege();
		privilege.setId(id);
		privilege.setName(name);
		privilege.setDescription(VALID_DESCRIPTION);
		privilege.setCreatedAt(VALID_DATETIME);
		privilege.setUpdatedAt(VALID_DATETIME);
		return privilege;
	}

	public PrivilegeInput buildInput() {
		return PrivilegeMapper.INSTANCE.toInput(build());
	}

	public PrivilegeModel buildModel() {
		return PrivilegeMapper.INSTANCE.toModel(build());
	}
};