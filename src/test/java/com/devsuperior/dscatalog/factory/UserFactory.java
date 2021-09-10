package com.devsuperior.dscatalog.factory;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.entities.User;

public class UserFactory {

	public static User createUser() {
		return new User(1L, "Frederico", "Mendes", "fredericomendes@gmail.com", "12345678");
	}
	
	public static UserDTO userDTO() {
		return new UserDTO(createUser());
	}
}