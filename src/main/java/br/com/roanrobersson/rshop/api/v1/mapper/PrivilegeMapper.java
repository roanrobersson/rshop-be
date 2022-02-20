package br.com.roanrobersson.rshop.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.roanrobersson.rshop.api.v1.dto.PrivilegeDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.PrivilegeInputDTO;
import br.com.roanrobersson.rshop.domain.model.Privilege;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PrivilegeMapper {

	public static final PrivilegeMapper INSTANCE = Mappers.getMapper(PrivilegeMapper.class);

	public abstract PrivilegeDTO toPrivilegeDTO(Privilege privilege);

	public abstract PrivilegeInputDTO toPrivilegeInputDTO(Privilege privilege);

	public abstract Privilege toPrivilege(PrivilegeInputDTO privilegeInputDTO);

	public abstract void update(PrivilegeInputDTO privilegeInputDTO, @MappingTarget Privilege privilege);

	public abstract void update(Privilege privilege, @MappingTarget PrivilegeInputDTO privilegeInputDTO);
}
