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

import br.com.roanrobersson.rshop.api.v1.dto.RoleDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RoleMapper {

	public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	@Autowired
	private PrivilegeService privilegeService;

	public abstract RoleDTO toRoleDTO(Role role);

	public abstract RoleInputDTO toRoleInputDTO(Role role);

	public abstract Role toRole(RoleInputDTO roleInputDTO);

	public abstract void update(RoleInputDTO roleInputDTO, @MappingTarget Role role);

	public abstract void update(Role role, @MappingTarget RoleInputDTO roleInputDTO);

	protected Set<UUID> privilegesToPrivilegesIds(Set<Privilege> privileges) {
		return privileges.stream().map(x -> x.getId()).collect(Collectors.toSet());
	}

	protected Set<Privilege> privilegesIdsToPrivileges(Set<UUID> privilegesIds) {
		Set<Privilege> privileges = new HashSet<>();
		for (UUID id : privilegesIds) {
			Privilege privilege = privilegeService.findById(id);
			privileges.add(privilege);
		}
		return privileges;
	}
}
