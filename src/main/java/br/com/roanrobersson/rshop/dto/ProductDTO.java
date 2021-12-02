package br.com.roanrobersson.rshop.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.services.validation.ProductValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ProductValid
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	@NotEmpty(message = "Produto sem categoria não é permitido")
	private Set<Long> categoriesId = new HashSet<>();

	@NotBlank(message = "Campo requerido")
	@Size(min = 3, max = 127, message = "Deve ter entre 8 e 127 caracteres")
	private String name;

	@NotBlank(message = "Campo requerido")
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	private String description;

	@Positive(message = "Preço deve ser positivo")
	private BigDecimal price;

	@NotBlank(message = "Campo requerido")
	private String imgUrl;
}
