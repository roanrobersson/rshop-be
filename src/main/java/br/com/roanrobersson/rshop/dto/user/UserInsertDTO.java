package br.com.roanrobersson.rshop.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.User;
import br.com.roanrobersson.rshop.services.validation.user.UserInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@UserInsertValid
@Getter @Setter @NoArgsConstructor
public class UserInsertDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;

	@Email(message = "Email inválido")
	@Size(min = 3, max = 255, message = "Deve ter entre 3 e 255 caracteres")
	private String email;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, max = 50, message = "Deve ter entre 8 e 50 caracteres")
	private String password;
	
	public UserInsertDTO(Long id, String firstName, String lastName, String email,
			String password, String primaryPhone, String secondaryPhone) {
		super(id, firstName, lastName, primaryPhone, secondaryPhone);
		this.email = email;
		this.password = password;
	}
	
	public UserInsertDTO(User entity) {
		super(entity);
		this.email = entity.getEmail();
		this.password = entity.getPassword();
	}
}
