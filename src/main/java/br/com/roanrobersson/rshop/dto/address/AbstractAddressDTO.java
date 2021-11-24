package br.com.roanrobersson.rshop.dto.address;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractAddressDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 20, message = "Deve ter entre 1 e 20 caracteres")
	private String nick;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 75, message = "Deve ter entre 1 e 75 caracteres")
	private String address;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 6, message = "Deve ter entre 1 e 6 dígitos")
	private String number;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 30, message = "Deve ter entre 1 e 30 caracteres")
	private String neighborhood;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 75, message = "Deve ter entre 1 e 75 caracteres")
	private String city;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 75, message = "Deve ter entre 1 e 75 caracteres")
	private String state;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 2, max = 2, message = "Deve ter 2 caracteres")
	private String uf;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, max = 8, message = "Deve ter 8 dígitos")
	private String postalCode;
	
	@Size(min = 1, max = 75, message = "Deve ter entre 1 e 8 caracteres")
	private String complement;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 75, message = "Deve ter entre 1 e 75 caracteres")
	private String referencePoint;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 11, max = 11, message = "Deve ter 11 dígitos")
	private String phone;
	
	public AbstractAddressDTO(Address entity) {
		this.nick = entity.getNick();
		this.address = entity.getAddress();
		this.number = entity.getNumber();
		this.neighborhood = entity.getNeighborhood();
		this.city = entity.getCity();
		this.state = entity.getState();
		this.uf = entity.getUf();
		this.postalCode = entity.getPostalCode();
		this.complement = entity.getComplement();
		this.referencePoint = entity.getReferencePoint();
		this.phone = entity.getPhone();
	}	
}
