package br.com.roanrobersson.rshop.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.CategoryInputValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@CategoryInputValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CategoryInput")
@ToString
public class CategoryInput {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 127, message = "Must be between 8 and 127 characters")
	@ApiModelProperty(example = "Cleaning", required = true)
	private String name;
}
