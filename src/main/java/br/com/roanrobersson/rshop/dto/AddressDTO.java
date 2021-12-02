package br.com.roanrobersson.rshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 1, max = 20, message = "Deve ter entre 1 e 20 caracteres")
	private String nick;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 10, max = 11, message = "Deve ter entre 10 e 11 caracteres")
	private String telephone;

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
}
