package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import org.modelmapper.ModelMapper;

import br.com.roanrobersson.rshop.domain.dto.RoleDTO;
import br.com.roanrobersson.rshop.domain.entities.Role;

public class RoleFactory {

	private static ModelMapper modelMapper = new ModelMapper();
	
	public static Role createRole() {
		return new Role(1L, "ROLE_TEST", Instant.now(), Instant.now());
	}
	
	public static RoleDTO createRoleInsertDTO() {
		return modelMapper.map(createRole(), RoleDTO.class);
	}
}
