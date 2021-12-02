package br.com.roanrobersson.rshop.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordDTO {

	@NotEmpty(message = "Campo obrigatório")
	@Size(min = 8, max = 50, message = "Deve ter entre 8 e 50 caracteres")
	private String newPassword;
}
