package br.com.roanrobersson.rshop.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 2, max = 50, message = "Deve ter entre 2 e 50 caracteres")
	private String firstName;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 2, max = 50, message = "Deve ter entre 2 e 50 caracteres")
	private String lastName;
	
	public AbstractUserDTO(User entity) {
		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
	}
}
