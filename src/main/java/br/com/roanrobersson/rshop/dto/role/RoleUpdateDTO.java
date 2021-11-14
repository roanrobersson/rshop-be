package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.services.validation.role.RoleUpdateValid;

@RoleUpdateValid
public class RoleUpdateDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public RoleUpdateDTO() {
		super();
	}

	public RoleUpdateDTO(Long id, String authority) {
		super(id, authority);
	}

	public RoleUpdateDTO(Role role) {
		super(role);
	}
}
