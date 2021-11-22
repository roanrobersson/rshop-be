package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.Role;

public class RoleResponseDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 3, message = "Deve ter 3 caracteres")
	private String id;
	
	public RoleResponseDTO() {
		super();
	}

	public RoleResponseDTO(String id, String authority) {
		super( authority);
		this.id = id;
	}

	public RoleResponseDTO(Role role) {
		super(role);
	}
}
