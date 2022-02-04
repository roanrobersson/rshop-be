package br.com.roanrobersson.rshop.factory;

import java.time.Instant;

import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Role;

public class RoleFactory {

	public static Role createRole() {
		return new Role(1L, "ROLE_TEST", Instant.now(), Instant.now());
	}

	public static RoleInputDTO createRoleInputDTO() {
		return RoleMapper.INSTANCE.toRoleInputDTO(createRole());
	}
}
