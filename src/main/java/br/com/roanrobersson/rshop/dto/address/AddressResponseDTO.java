package br.com.roanrobersson.rshop.dto.address;

import br.com.roanrobersson.rshop.entities.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AddressResponseDTO extends AbstractAddressDTO {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public AddressResponseDTO(Address entity) {
		super(entity);
		this.id = entity.getId();
	}
}
