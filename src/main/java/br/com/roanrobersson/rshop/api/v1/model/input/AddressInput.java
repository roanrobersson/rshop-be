package br.com.roanrobersson.rshop.api.v1.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.AddressInputValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AddressInputValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AddressInput")
@ToString(of = { "nick" })
public class AddressInput {

	@NotBlank
	@Size(min = 1, max = 20)
	@ApiModelProperty(example = "Home", required = true)
	private String nick;

	@NotBlank
	@Size(min = 10, max = 11)
	@ApiModelProperty(example = "54998204476", required = true)
	private String telephone;

	@NotBlank
	@Size(min = 1, max = 75)
	@ApiModelProperty(example = "342 Lake St.", required = true)
	private String address;

	@NotBlank
	@Size(min = 1, max = 6)
	@ApiModelProperty(example = "11228", required = true)
	private String number;

	@NotBlank
	@Size(min = 1, max = 30)
	@ApiModelProperty(example = "Arbor Hill", required = true)
	private String neighborhood;

	@NotBlank
	@Size(min = 1, max = 75)
	@ApiModelProperty(example = "Albany", required = true)
	private String city;

	@NotBlank
	@Size(min = 1, max = 75)
	@ApiModelProperty(example = "New York", required = true)
	private String state;

	@NotBlank
	@Size(min = 2, max = 2)
	@ApiModelProperty(example = "NY", required = true)
	private String uf;

	@NotBlank
	@Size(min = 8, max = 8)
	@ApiModelProperty(example = "45879635", required = true)
	private String postalCode;

	@Size(min = 1, max = 75)
	@ApiModelProperty(example = "Floor 16", required = false)
	private String complement;

	@NotBlank
	@Size(min = 1, max = 75)
	@ApiModelProperty(example = "McDonald's", required = true)
	private String referencePoint;
}
