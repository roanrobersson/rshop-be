package br.com.roanrobersson.rshop.factories;

import java.time.Instant;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.roanrobersson.rshop.api.v1.dto.UserDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.domain.Role;
import br.com.roanrobersson.rshop.domain.User;

public class UserFactory {

	private static ModelMapper mapper = new ModelMapper();
	
	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static User createUser() {
		Set<Role> roles = Set.of(RoleFactory.createRole());
		return new User(1L, roles, null, null, "Roan", "Roan Oliveira", Instant.now(), "86213939059", "355144724",
				"kevinbrown@gmail.com", passwordEncoder.encode("a3g&3Pd#"), "57991200038", null, Instant.now(),
				Instant.now(), Instant.now());
	}

	public static UserDTO createUserDTO() {
		return mapper.map(createUser(), UserDTO.class);
	}
	
	public static UserInsertDTO createUserInsertDTO() {
		return mapper.map(createUser(), UserInsertDTO.class);
	}
	
	public static UserUpdateDTO createUserUpdateDTO() {
		return mapper.map(createUser(), UserUpdateDTO.class);
	}
}