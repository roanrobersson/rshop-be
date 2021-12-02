package br.com.roanrobersson.rshop.dto;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 10, max = 11, message = "Deve ter entre 10 e 11 caracteres")
	private String primaryTelephone;

	@Size(min = 10, max = 11, message = "Deve ter entre 10 e 11 caracteres")
	private String secondaryTelephone;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 2, max = 50, message = "Deve ter entre 2 e 50 caracteres")
	private String firstName;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 5, max = 100, message = "Deve ter entre 5 e 100 caracteres")
	private String name;

	@Past(message = "A data de nascimento deve ser no passado")
	private Instant birthDate;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 11, max = 11, message = "Deve ter 11 caracteres")
	private String cpf;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 5, max = 14, message = "Deve ter entre 1 e 14 caracteres")
	private String rg;

	@Positive
	public Long imageId;
}
