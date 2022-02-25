package br.com.roanrobersson.rshop.api.v1.model;

import java.util.UUID;

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
public class CategoryModel {

	@ApiModelProperty(example = "821e3c677f2246af978cb6269cb15387")
	private UUID id;

	@ApiModelProperty(example = "Cleaning")
	private String name;
}
