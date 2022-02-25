package br.com.roanrobersson.rshop.api.v1.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.api.v1.model.RoleModel;
import br.com.roanrobersson.rshop.api.v1.model.input.RoleInput;
import br.com.roanrobersson.rshop.api.v1.model.input.id.PrivilegeIdInput;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RoleMapper {

	public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	@Autowired
	private PrivilegeService privilegeService;

	public abstract RoleModel toRoleModel(Role role);

	public abstract RoleInput toRoleInput(Role role);

	public abstract Role toRole(RoleInput roleInput);

	public abstract void update(RoleInput roleInput, @MappingTarget Role role);

	public abstract void update(Role role, @MappingTarget RoleInput roleInput);

	protected String uuidToString(UUID uuid) {
		return uuid.toString();
	}

	protected Set<UUID> privilegesToPrivilegesIds(Set<Privilege> privileges) {
		return privileges.stream().map(x -> x.getId()).collect(Collectors.toSet());
	}

	protected Set<Privilege> privilegesIdsToPrivileges(Set<PrivilegeIdInput> privilegesIds) {
		Set<Privilege> privileges = new HashSet<>();
		for (PrivilegeIdInput input : privilegesIds) {
			UUID id = UUID.fromString(input.getId());
			Privilege privilege = privilegeService.findById(id);
			privileges.add(privilege);
		}
		return privileges;
	}
}
