package br.com.roanrobersson.rshop.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.domain.validation.CategoryInputValid;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "CategoryInput")
@ToString
public class CategoryInput {

	@NotBlank
	@Size(min = 3, max = 127)
	@Schema(example = "Cleaning", required = true)
	private String name;
}
