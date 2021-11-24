package br.com.roanrobersson.rshop.dto.address;

import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.services.validation.address.AddressInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AddressInsertValid
@Getter @Setter @NoArgsConstructor
public class AddressInsertDTO extends AbstractAddressDTO {
	private static final long serialVersionUID = 1L;
	
	public AddressInsertDTO(String nick, String address, String number,
			String neighborhood, String city, String state, String uf, String postalCode, 
			String complement, String referencePoint, String phone) {
		super(nick, address, number, neighborhood, city, state, uf, postalCode, complement, referencePoint, phone);
	}
	
	public AddressInsertDTO(Address entity) {
		super(entity);
	}
}
