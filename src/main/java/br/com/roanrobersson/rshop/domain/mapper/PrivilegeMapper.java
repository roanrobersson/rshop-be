package br.com.roanrobersson.rshop.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.domain.dto.input.PrivilegeInput;
import br.com.roanrobersson.rshop.domain.dto.model.PrivilegeModel;
import br.com.roanrobersson.rshop.domain.model.Privilege;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PrivilegeMapper {

	public static final PrivilegeMapper INSTANCE = Mappers.getMapper(PrivilegeMapper.class);

	public abstract PrivilegeModel toModel(Privilege privilege);

	public abstract PrivilegeInput toInput(Privilege privilege);

	public abstract Privilege toPrivilege(PrivilegeInput privilegeInput);

	public Page<PrivilegeModel> toModelPage(Page<Privilege> privileges) {
		return privileges.map(x -> this.toModel(x));
	};

	public abstract void update(PrivilegeInput privilegeInput, @MappingTarget Privilege privilege);
}
