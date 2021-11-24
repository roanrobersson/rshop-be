package br.com.roanrobersson.rshop.dto.address;

import br.com.roanrobersson.rshop.dto.user.UserResponseDTO;
import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.services.validation.address.AddressUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AddressUpdateValid
@Getter @Setter @NoArgsConstructor
public class AddressUpdateDTO extends AbstractAddressDTO {
	private static final long serialVersionUID = 1L;
	
	public AddressUpdateDTO(String nick, String address, String number,
			String neighborhood, String city, String state, String uf, String postalCode, 
			String complement, String referencePoint, UserResponseDTO user, String phone) {
		super(nick, address, number, neighborhood, city, state, uf, postalCode, complement, referencePoint, phone);
	}
	
	public AddressUpdateDTO(Address entity) {
		super(entity);
	}
}
