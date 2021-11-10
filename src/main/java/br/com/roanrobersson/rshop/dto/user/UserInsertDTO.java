package br.com.roanrobersson.rshop.dto.user;

import javax.validation.constraints.Email;

import br.com.roanrobersson.rshop.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;
	
	@Email(message = "Favor entrar um email válido")
	private String email;
	
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
