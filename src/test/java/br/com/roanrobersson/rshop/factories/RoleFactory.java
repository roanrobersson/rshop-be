package br.com.roanrobersson.rshop.factories;

import br.com.roanrobersson.rshop.dto.RoleDTO;
import br.com.roanrobersson.rshop.entities.Role;

public class RoleFactory {

	public static Role createRole() {
		return new Role(1L, "ROLE_TEST");
	}
	
	public static RoleDTO createRoleDTO() {
		return new RoleDTO(createRole());
	}
}
