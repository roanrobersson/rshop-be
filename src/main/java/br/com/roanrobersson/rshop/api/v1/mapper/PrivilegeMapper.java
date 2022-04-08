package br.com.roanrobersson.rshop.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.roanrobersson.rshop.api.v1.model.PrivilegeModel;
import br.com.roanrobersson.rshop.api.v1.model.input.PrivilegeInput;
import br.com.roanrobersson.rshop.domain.model.Privilege;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PrivilegeMapper {

	public static final PrivilegeMapper INSTANCE = Mappers.getMapper(PrivilegeMapper.class);

	public abstract PrivilegeModel toPrivilegeModel(Privilege privilege);

	public abstract PrivilegeInput toPrivilegeInput(Privilege privilege);

	public abstract Privilege toPrivilege(PrivilegeInput privilegeInput);

	public abstract void update(PrivilegeInput privilegeInput, @MappingTarget Privilege privilege);
}
