package br.com.roanrobersson.rshop.builder;

import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.aNonExistingPrivilege;
import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.anExistingPrivilege;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.roanrobersson.rshop.domain.dto.input.RoleInput;
import br.com.roanrobersson.rshop.domain.dto.model.RoleModel;
import br.com.roanrobersson.rshop.domain.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import lombok.Getter;

@Getter
public class RoleBuilder {

	public static final Long EXISTING_ID = 1L;
	public static final Long ANOTHER_EXISTING_ID = 2L;
	public static final Long NON_EXISTING_ID = 999999L;
	public static final Long DEPENDENT_ID = 3L;
	public static final String NON_EXISTING_NAME = "NON EXISTING ROLE NAME";
	public static final String EXISTING_NAME = "CLIENT";
	public static final String ANOTHER_EXISTING_NAME = "OPERATOR";
	public static final OffsetDateTime VALID_DATETIME = OffsetDateTime.parse("2020-10-20T03:00:00Z");

	private Long id = EXISTING_ID;
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

	public RoleBuilder withId(Long id) {
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
		Role role = new Role();
		role.setId(id);
		role.getPrivileges().addAll(privileges);
		role.setName(name);
		role.setCreatedAt(VALID_DATETIME);
		role.setUpdatedAt(VALID_DATETIME);
		return role;
	}

	public RoleInput buildInput() {
		return RoleMapper.INSTANCE.toInput(build());
	}

	public RoleModel buildModel() {
		return RoleMapper.INSTANCE.toModel(build());
	}
}
