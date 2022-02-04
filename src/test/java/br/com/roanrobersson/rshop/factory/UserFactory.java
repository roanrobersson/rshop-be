package br.com.roanrobersson.rshop.factory;

import java.time.Instant;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.roanrobersson.rshop.api.v1.dto.UserDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.UserMapper;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;

public class UserFactory {

	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static User createUser() {
		Set<Role> roles = Set.of(RoleFactory.createRole());
		return new User(1L, roles, null, "Pedro", "Pedro Oliveira", Instant.now(), "86213939059", "355144724",
				"kevinbrown@gmail.com", passwordEncoder.encode("a3g&3Pd#"), "57991200038", null, Instant.now(),
				Instant.now(), Instant.now());
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