package br.com.roanrobersson.rshop.api.v1.dto;

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
@ApiModel(value = "Category")
@ToString
public class CategoryDTO {

	@ApiModelProperty(example = "3")
	private Long id;

	@ApiModelProperty(example = "Cleaning")
	private String name;
}