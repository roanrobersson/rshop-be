package br.com.roanrobersson.rshop.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.User;
import br.com.roanrobersson.rshop.services.validation.user.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;
	
	@Email(message = "Email inválido")
	@Size(min = 3, max = 255, message = "Deve ter entre 3 e 255 caracteres")
	private String email;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, max = 30, message = "Deve ter entre 8 e 30 caracteres")
	private String password;
	
	public UserInsertDTO(){
		super();
	}
	
	public UserInsertDTO(Long id, String firstName, String lastName, String email, String password) {
		super(id, firstName, lastName);
		this.email = email;
		this.password = password;
	}
	
	public UserInsertDTO(User entity) {
		super(entity);
		this.email = entity.getEmail();
		this.password = entity.getPassword();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
