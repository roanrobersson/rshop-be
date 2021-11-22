package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.services.validation.role.RoleInsertValid;

@RoleInsertValid
public class RoleInsertDTO extends AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 3, message = "Deve ter 3 caracteres")
	private String id;
	
	public RoleInsertDTO() {
		super();
	}

	public RoleInsertDTO(String id, String authority) {
		super(authority);
		this.id = id;
	}

	public RoleInsertDTO(Role role) {
		super(role);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
