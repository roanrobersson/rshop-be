package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Role;

public class RoleResponseDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public RoleResponseDTO() {
		super();
	}

	public RoleResponseDTO(Long id, String authority) {
		super(id, authority);
	}

	public RoleResponseDTO(Role role) {
		super(role);
	}
}
