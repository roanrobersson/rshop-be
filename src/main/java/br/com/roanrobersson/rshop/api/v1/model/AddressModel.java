package br.com.roanrobersson.rshop.api.v1.model;

import java.util.UUID;

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
@ApiModel(value = "Address")
@ToString(of = { "id", "nick", "main" })
public class AddressModel {

	@ApiModelProperty(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@ApiModelProperty(example = "Home")
	private String nick;

	@ApiModelProperty(example = "54998204476")
	private String telephone;

	@ApiModelProperty(example = "342 Lake St.")
	private String address;

	@ApiModelProperty(example = "11228")
	private String number;

	@ApiModelProperty(example = "Arbor Hill")
	private String neighborhood;

	@ApiModelProperty(example = "Albany")
	private String city;

	@ApiModelProperty(example = "New York")
	private String state;

	@ApiModelProperty(example = "NY")
	private String uf;

	@ApiModelProperty(example = "45879635")
	private String postalCode;

	@ApiModelProperty(example = "Floor 16")
	private String complement;

	@ApiModelProperty(example = "McDonald's")
	private String referencePoint;

	@ApiModelProperty(example = "true")
	private Boolean main;
}
