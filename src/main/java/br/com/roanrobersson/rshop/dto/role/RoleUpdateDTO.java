package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.services.validation.role.RoleUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RoleUpdateValid
@Getter @Setter @NoArgsConstructor
public class RoleUpdateDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public RoleUpdateDTO(String authority) {
		super(authority);
	}

	public RoleUpdateDTO(Role role) {
		super(role);
	}
}
