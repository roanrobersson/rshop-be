package br.com.roanrobersson.rshop.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.roanrobersson.rshop.api.v1.dto.UserDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;

public class UserFactory {

	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static Instant instant = Instant.parse("2020-10-20T03:00:00Z");
	private static LocalDate date = LocalDate.parse("1993-05-15");
	private static UUID id = UUID.fromString("821e3c67-7f22-46af-978c-b6269cb15387");
	private static String password = passwordEncoder.encode("a3g&3Pd#");

	public static User createUser() {
		Set<Role> roles = Set.of(RoleFactory.createRole());
		return new User(id, roles, null, "Pedro", "Pedro Oliveira", date, "86213939059", "355144724",
				"kevinbrown@gmail.com", password, "57991200038", null, instant, instant, instant, instant);
	}

	public static UserDTO createUserDTO() {
		return UserMapper.INSTANCE.toUserDTO(createUser());
	}

	public static UserInsertDTO createUserInsertDTO() {
		return UserMapper.INSTANCE.toUserInsertDTO(createUser());
	}

	public static UserUpdateDTO createUserUpdateDTO() {
		return UserMapper.INSTANCE.toUserUpdateDTO(createUser());
	}
}