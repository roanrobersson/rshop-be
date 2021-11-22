package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.Role;

public abstract class AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 30, message = "Deve ter entre 3 e 127 caracteres")
	private String authority;
	
	public AbstractRoleDTO() {
	}

	public AbstractRoleDTO(String authority) {
		this.authority = authority;
	}

	public AbstractRoleDTO(Role role) {
		authority = role.getAuthority();
	}
	

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
