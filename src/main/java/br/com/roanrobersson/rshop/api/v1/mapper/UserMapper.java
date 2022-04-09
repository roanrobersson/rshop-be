package br.com.roanrobersson.rshop.api.v1.mapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.roanrobersson.rshop.api.v1.model.UserModel;
import br.com.roanrobersson.rshop.api.v1.model.input.UserInsert;
import br.com.roanrobersson.rshop.api.v1.model.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

	public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRolesIds")
	@Mapping(source = "roles", target = "privileges", qualifiedByName = "rolesToPrivilegesIds")
	public abstract UserModel toUserModel(User user);

	public abstract UserInsert toUserInsert(User user);

	public abstract UserUpdate toUserUpdate(User user);

	@Mapping(target = "password", ignore = true)
	public abstract User toUser(UserInsert userInsert);

	public abstract void update(UserUpdate userUpdate, @MappingTarget User user);

	@Named("rolesToRolesIds")
	protected Set<UUID> rolesToRolesIds(Set<Role> roles) {
		return roles.stream().map(Role::getId).collect(Collectors.toSet());
	}

	@Named("rolesToPrivilegesIds")
	protected Set<UUID> rolesToPrivilegesIds(Set<Role> roles) {
		return roles.stream().flatMap(r -> r.getPrivileges().stream()).map(Privilege::getId)
				.collect(Collectors.toSet());
	}
}
