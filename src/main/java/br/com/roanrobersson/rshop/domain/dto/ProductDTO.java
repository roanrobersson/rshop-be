package br.com.roanrobersson.rshop.domain.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.services.validation.ProductValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ProductValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	
	@ApiModelProperty(hidden = true)
	private Long id;
	
	@NotEmpty(message = "Produto sem categoria não é permitido")
	@Builder.Default
	private Set<Long> categoriesIds = new HashSet<>();

	@NotBlank(message = "Campo requerido")
	@Size(min = 3, max = 127, message = "Deve ter entre 8 e 127 caracteres")
	private String name;

	@NotBlank(message = "Campo requerido")
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	private String description;

	@Positive(message = "Preço deve ser positivo")
	@Digits(integer = 6, fraction = 2, message = "O valor deve serguir o formato: 999999,99")
	private BigDecimal price;

	@NotBlank(message = "Campo requerido")
	private String imgUrl;
}
