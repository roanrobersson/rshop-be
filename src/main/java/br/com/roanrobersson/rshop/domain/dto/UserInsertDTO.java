package br.com.roanrobersson.rshop.domain.dto;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.services.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@UserInsertValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDTO {

	@Positive
	public Long imageId;

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

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 50, message = "Deve ter entre 3 e 50 caracteres")
	private String email;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, max = 50, message = "Deve ter entre 8 e 50 caracteres")
	private String password;

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 10, max = 11, message = "Deve ter entre 10 e 11 caracteres")
	private String primaryTelephone;

	@Size(min = 10, max = 11, message = "Deve ter entre 10 e 11 caracteres")
	private String secondaryTelephone;
}
