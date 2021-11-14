package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.services.validation.role.RoleInsertValid;

@RoleInsertValid
public class RoleInsertDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public RoleInsertDTO() {
		super();
	}

	public RoleInsertDTO(Long id, String authority) {
		super(id, authority);
	}

	public RoleInsertDTO(Role role) {
		super(role);
	}
}
