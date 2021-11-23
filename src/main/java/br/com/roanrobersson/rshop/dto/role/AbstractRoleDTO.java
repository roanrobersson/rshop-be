package br.com.roanrobersson.rshop.dto.role;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 30, message = "Deve ter entre 3 e 127 caracteres")
	private String authority;
	
	public AbstractRoleDTO(Role role) {
		authority = role.getAuthority();
	}
}
