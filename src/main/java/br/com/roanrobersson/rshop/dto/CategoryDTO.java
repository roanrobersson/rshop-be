package br.com.roanrobersson.rshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.services.validation.CategoryValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@CategoryValid
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 127, message = "Deve ter entre 8 e 127 caracteres")
	private String name;
}
