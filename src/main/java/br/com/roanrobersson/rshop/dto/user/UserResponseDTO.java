package br.com.roanrobersson.rshop.dto.user;

import br.com.roanrobersson.rshop.entities.User;
import br.com.roanrobersson.rshop.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserResponseDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	public UserResponseDTO(){
		super();
	}
	
	public UserResponseDTO(User entity) {
		super(entity);
		this.email = entity.getEmail();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
