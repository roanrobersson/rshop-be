package br.com.roanrobersson.rshop.dto.response;

import java.time.Instant;

import br.com.roanrobersson.rshop.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressResponseDTO {

	private Long id;
	private String nick;
	private String address;
	private String number;
	private String neighborhood;
	private String city;
	private String state;
	private String uf;
	private String postalCode;
	private String complement;
	private String referencePoint;
	private String telephone;
	private Boolean main;
	private Instant createdAt;
	private Instant updatedAt;

	public AddressResponseDTO(Address address) {
		this.id = address.getId();
		this.nick = address.getNick();
		this.address = address.getAddress();
		this.number = address.getNumber();
		this.neighborhood = address.getNeighborhood();
		this.city = address.getCity();
		this.state = address.getState();
		this.uf = address.getUf();
		this.postalCode = address.getPostalCode();
		this.complement = address.getComplement();
		this.referencePoint = address.getReferencePoint();
		this.telephone = address.getTelephone();
		this.main = address.getMain();
		this.createdAt = address.getCreatedAt();
		this.updatedAt = address.getUpdatedAt();
	}
}
