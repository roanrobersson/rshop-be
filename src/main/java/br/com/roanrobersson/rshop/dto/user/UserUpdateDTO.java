package br.com.roanrobersson.rshop.dto.user;

import br.com.roanrobersson.rshop.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;

	public UserUpdateDTO(){
		super();
	}
}
