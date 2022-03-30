package br.com.roanrobersson.rshop.builder;

import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.anExistingPrivilege;
import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.aNonExistingPrivilege;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.api.v1.model.RoleModel;
import br.com.roanrobersson.rshop.api.v1.model.input.RoleInput;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import lombok.Getter;

@Getter
public class RoleBuilder {

	public static final UUID EXISTING_ID = UUID.fromString("18aace1e-f36a-4d71-b4d1-124387d9b63a");
	public static final UUID ANOTHER_EXISTING_ID = UUID.fromString("eb1ffb79-5dfb-4b13-b615-eae094a06207");
	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-4000-0000-000000000000");
	public static final UUID DEPENDENT_ID = UUID.fromString("18aace1e-f36a-4d71-b4d1-124387d9b63a");
	public static final String NON_EXISTING_NAME = "NON EXISTING ROLE NAME";
	public static final String EXISTING_NAME = "CLIENT";
	public static final String ANOTHER_EXISTING_NAME = "OPERATOR";
	public static final Instant VALID_INSTANT = Instant.parse("2020-10-20T03:00:00Z");

	private UUID id = EXISTING_ID;
	private String name = EXISTING_NAME;
	private Set<Privilege> privileges = new HashSet<>();

	public static RoleBuilder aRole() {
		return new RoleBuilder();
	}

	public static RoleBuilder anExistingRole() {
		RoleBuilder builder = new RoleBuilder();
		builder.id = EXISTING_ID;
		builder.name = EXISTING_NAME;
		builder.privileges.add(anExistingPrivilege().build());
		return builder;
	}

	public static RoleBuilder aNonExistingRole() {
		RoleBuilder builder = new RoleBuilder();
		builder.id = NON_EXISTING_ID;
		builder.name = NON_EXISTING_NAME;
		builder.privileges.add(aNonExistingPrivilege().build());
		return builder;
	}

	public RoleBuilder withId(UUID id) {
		this.id = id;
		return this;
	}

	public RoleBuilder withExistingId() {
		this.id = EXISTING_ID;
		return this;
	}

	public RoleBuilder withAnotherExistingId() {
		this.id = ANOTHER_EXISTING_ID;
		return this;
	}

	public RoleBuilder withNonExistingId() {
		this.id = NON_EXISTING_ID;
		return this;
	}

	public RoleBuilder withDependentId() {
		this.id = DEPENDENT_ID;
		return this;
	}

	public RoleBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public RoleBuilder withNonExistingName() {
		this.name = NON_EXISTING_NAME;
		return this;
	}

	public RoleBuilder withExistingName() {
		this.name = EXISTING_NAME;
		return this;
	}

	public RoleBuilder withAnotherExistingName() {
		this.name = ANOTHER_EXISTING_NAME;
		return this;
	}

	public RoleBuilder withPrivilege(Privilege privilege) {
		privileges.add(privilege);
		return this;
	}

	public RoleBuilder withExistingPrivilege() {
		privileges.add(anExistingPrivilege().build());
		return this;
	}

	public RoleBuilder withNonExistingPrivilege() {
		privileges.add(aNonExistingPrivilege().build());
		return this;
	}

	public RoleBuilder withoutPrivileges() {
		privileges.clear();
		return this;
	}

	public Role build() {
		return new Role(id, privileges, name, VALID_INSTANT, VALID_INSTANT);
	}

	public RoleInput buildInput() {
		return RoleMapper.INSTANCE.toRoleInput(build());
	}

	public RoleModel buildModel() {
		return RoleMapper.INSTANCE.toRoleModel(build());
	}
}
