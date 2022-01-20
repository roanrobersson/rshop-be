package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import br.com.roanrobersson.rshop.domain.dto.UserDTO;
import br.com.roanrobersson.rshop.domain.entities.User;

public class UserFactory {

	public static User createUser() {
		return new User(1L, "fredericomendes@gmail.com", "12345678", Instant.parse("2020-10-20T03:00:00Z"));
	}
	
	public static UserDTO createUserDTO() {
		return new UserDTO(createUser());
	}
}