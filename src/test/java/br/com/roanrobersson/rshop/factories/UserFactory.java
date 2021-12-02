package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import br.com.roanrobersson.rshop.dto.response.UserResponseDTO;
import br.com.roanrobersson.rshop.entities.User;

public class UserFactory {

	public static User createUser() {
		return new User(1L, "fredericomendes@gmail.com", "12345678", Instant.parse("2020-10-20T03:00:00Z"));
	}
	
	public static UserResponseDTO createUserDTO() {
		return new UserResponseDTO(createUser());
	}
}