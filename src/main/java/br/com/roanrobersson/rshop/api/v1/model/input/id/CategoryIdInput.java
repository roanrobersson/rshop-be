package br.com.roanrobersson.rshop.api.v1.model.input.id;

import javax.validation.constraints.NotNull;

import br.com.roanrobersson.rshop.core.validation.UUIDValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryIdInput {

	@NotNull
	@UUIDValid
	@ApiModelProperty(example = "753dad79-2a1f-4f5c-bbd1-317a53587518")
	private String id;
}
