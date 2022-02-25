package br.com.roanrobersson.rshop.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PrivilegeInput")
@ToString
public class PrivilegeInput {

	@NotBlank(message = "Required field")
	@Size(min = 3, max = 30, message = "Must be between 3 and 30 characters")
	@ApiModelProperty(example = "EDIT_CATEGORIES", required = true)
	private String name;

	@NotBlank(message = "Required field")
	@Size(min = 10, max = 100, message = "Must be between 10 and 100 characters")
	@ApiModelProperty(example = "Allow edit categories", required = true)
	private String description;
}
