package br.com.roanrobersson.rshop.factories;

import br.com.roanrobersson.rshop.dto.user.AbstractUserDTO;
import br.com.roanrobersson.rshop.dto.user.UserResponseDTO;
import br.com.roanrobersson.rshop.entities.User;

public class UserFactory {

	public static User createUser() {
		return new User(1L, "Frederico", "Mendes", "fredericomendes@gmail.com", "12345678");
	}
	
	public static AbstractUserDTO userDTO() {
		return new UserResponseDTO(createUser());
	}
}