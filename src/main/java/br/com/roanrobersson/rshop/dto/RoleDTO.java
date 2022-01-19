package br.com.roanrobersson.rshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.services.validation.RoleValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RoleValid
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 30, message = "Deve ter entre 3 e 30 caracteres")
	private String name;
}
