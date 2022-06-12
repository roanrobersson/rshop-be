package br.com.roanrobersson.rshop.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import br.com.roanrobersson.rshop.domain.dto.input.AddressInput;
import br.com.roanrobersson.rshop.domain.dto.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.dto.model.AddressModel;
import br.com.roanrobersson.rshop.domain.model.Address;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AddressMapper {

	public static final AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	public abstract AddressModel toModel(Address address);

	public abstract AddressInput toInput(Address address);

	public abstract Address toAddress(AddressInput addressInput);

	public Page<AddressModel> toModelPage(Page<Address> addresses) {
		return addresses.map(x -> this.toModel(x));
	};

	public abstract void update(AddressInput addressInput, @MappingTarget Address address);

	public abstract void update(Address address, @MappingTarget CategoryInput addressInput);
}
