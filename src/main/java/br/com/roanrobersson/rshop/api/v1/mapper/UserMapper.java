package br.com.roanrobersson.rshop.api.v1.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.api.v1.dto.UserDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserInsertDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.UserUpdateDTO;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.service.RoleService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

	public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Autowired
	private RoleService roleService;

	@Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRolesIds")
	@Mapping(source = "roles", target = "privileges", qualifiedByName = "rolesToPrivilegesIds")
	public abstract UserDTO toUserDTO(User user);

	public abstract UserInsertDTO toUserInsertDTO(User user);

	public abstract UserUpdateDTO toUserUpdateDTO(User user);

	@Mapping(target = "password", ignore = true)
	public abstract User toUser(UserInsertDTO userInsertDTO);

	public abstract User toUser(UserUpdateDTO userUpdateDTO);

	@Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRolesIds")
	@Mapping(source = "roles", target = "privileges", qualifiedByName = "rolesToPrivilegesIds")
	public abstract void update(User user, @MappingTarget UserDTO userDTO);

	@Mapping(target = "password", ignore = true)
	public abstract void update(UserInsertDTO userInsertDTO, @MappingTarget User user);

	public abstract void update(User user, @MappingTarget UserInsertDTO userInsertDTO);

	public abstract void update(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

	public abstract void update(User user, @MappingTarget UserUpdateDTO userUpdateDTO);

	@Named("rolesToRolesIds")
	protected Set<UUID> rolesToRolesIds(Set<Role> roles) {
		return roles.stream().map(x -> x.getId()).collect(Collectors.toSet());
	}

	@Named("rolesToPrivilegesIds")
	protected Set<UUID> rolesToPrivilegesIds(Set<Role> roles) {
		return roles.stream().map(x -> x.getId()).collect(Collectors.toSet());
	}

	protected Set<Role> rolesIdsToRoles(Set<UUID> rolesIds) {
		Set<Role> roles = new HashSet<>();
		for (UUID id : rolesIds) {
			Role role = roleService.findById(id);
			roles.add(role);
		}
		return roles;
	}
}
