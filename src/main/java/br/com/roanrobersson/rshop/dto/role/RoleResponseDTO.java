package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RoleResponseDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	public RoleResponseDTO(String id, String authority) {
		super(authority);
		this.id = id;
	}

	public RoleResponseDTO(Role role) {
		super(role);
		this.id = role.getId();
	}
}
