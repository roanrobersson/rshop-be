package br.com.roanrobersson.rshop.factories;

import java.time.Instant;
import java.util.Set;

import org.modelmapper.ModelMapper;

import br.com.roanrobersson.rshop.api.v1.dto.RoleDTO;
import br.com.roanrobersson.rshop.domain.Privilege;
import br.com.roanrobersson.rshop.domain.Role;

public class RoleFactory {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Role createRole() {
		Set<Privilege> privileges = Set.of(PrivilegeFactory.createPrivilege());
		return new Role(1L, privileges, "ROLE_TEST", Instant.now(), Instant.now());
	}
	
	public static RoleDTO createRoleInsertDTO() {
		return mapper.map(createRole(), RoleDTO.class);
	}
}
