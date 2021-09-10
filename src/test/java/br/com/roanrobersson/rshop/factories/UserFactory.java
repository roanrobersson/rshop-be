package br.com.roanrobersson.rshop.factories;

import br.com.roanrobersson.rshop.dto.UserDTO;
import br.com.roanrobersson.rshop.entities.User;

public class UserFactory {

	public static User createUser() {
		return new User(1L, "Frederico", "Mendes", "fredericomendes@gmail.com", "12345678");
	}
	
	public static UserDTO userDTO() {
		return new UserDTO(createUser());
	}
}