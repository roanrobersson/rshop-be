package br.com.roanrobersson.rshop.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.roanrobersson.rshop.api.v1.model.AddressModel;
import br.com.roanrobersson.rshop.api.v1.model.input.AddressInput;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.model.Address;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AddressMapper {

	public static final AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	public abstract AddressModel toAddressModel(Address address);

	public abstract AddressInput toAddressInput(Address address);

	public abstract Address toAddress(AddressInput addressInput);

	public abstract void update(AddressInput addressInput, @MappingTarget Address address);

	public abstract void update(Address address, @MappingTarget CategoryInput addressInput);
}
