package br.com.roanrobersson.rshop.domain.mapper;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.core.config.MapStructConfig;
import br.com.roanrobersson.rshop.domain.dto.input.RoleInput;
import br.com.roanrobersson.rshop.domain.dto.input.id.PrivilegeIdInput;
import br.com.roanrobersson.rshop.domain.dto.model.RoleModel;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.service.PrivilegeService;

@Mapper(config = MapStructConfig.class)
public abstract class RoleMapper {

	public static final RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	@Autowired
	private PrivilegeService privilegeService;

	public abstract RoleModel toModel(Role role);

	public abstract RoleInput toInput(Role role);

	public abstract Role toRole(RoleInput roleInput);

	public abstract void update(RoleInput roleInput, @MappingTarget Role role);

	public abstract Set<RoleModel> toModelSet(Set<Role> roles);

	public Page<RoleModel> toModelPage(Page<Role> roles) {
		return roles.map(x -> this.toModel(x));
	};

	protected Set<Privilege> privilegesIdsToPrivileges(Set<PrivilegeIdInput> privilegesIds) {
		Set<Privilege> privileges = new HashSet<>();
		for (PrivilegeIdInput input : privilegesIds) {
			Long id = input.getId();
			Privilege privilege = privilegeService.findById(id);
			privileges.add(privilege);
		}
		return privileges;
	}
}
