package br.com.roanrobersson.rshop.factory;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;

public class RoleFactory {

	private static Instant instant = Instant.parse("2020-10-20T03:00:00Z");
	private static UUID id = UUID.fromString("18aace1e-f36a-4d71-b4d1-124387d9b63a");
	
	public static Role createRole() {
		Set<Privilege> privileges = Set.of(PrivilegeFactory.createPrivilege());
		return new Role(id, privileges, "ROLE_TEST", instant, instant);
	}

	public static RoleInputDTO createRoleInputDTO() {
		return RoleMapper.INSTANCE.toRoleInputDTO(createRole());
	}
}
