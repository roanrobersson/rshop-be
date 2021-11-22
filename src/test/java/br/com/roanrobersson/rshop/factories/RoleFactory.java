package br.com.roanrobersson.rshop.factories;

import br.com.roanrobersson.rshop.dto.role.RoleInsertDTO;
import br.com.roanrobersson.rshop.entities.Role;

public class RoleFactory {

	public static Role createRole() {
		return new Role("tes", "ROLE_TEST");
	}
	
	public static RoleInsertDTO createRoleDTO() {
		return new RoleInsertDTO(createRole());
	}
}
