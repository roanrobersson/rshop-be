package br.com.roanrobersson.rshop.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.services.validation.UserInsertValid;

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
