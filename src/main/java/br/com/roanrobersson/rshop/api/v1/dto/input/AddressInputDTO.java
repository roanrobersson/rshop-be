package br.com.roanrobersson.rshop.api.v1.dto.input;

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
public class AddressInputDTO {

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
	@ApiModelProperty(example = "Home", required = true)
	private String nick;

	@NotBlank(message = "Required field")
	@Size(min = 10, max = 11, message = "Must be between 10 and 11 characters")
	@ApiModelProperty(example = "54998204476", required = true)
	private String telephone;

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 75, message = "Must be between 1 and 75 characters")
	@ApiModelProperty(example = "342 Lake St.", required = true)
	private String address;

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 6, message = "Must be between 1 and 6 characters")
	@ApiModelProperty(example = "11228", required = true)
	private String number;

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
	@ApiModelProperty(example = "Arbor Hill", required = true)
	private String neighborhood;

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 75, message = "Must be between 1 and 75 characters")
	@ApiModelProperty(example = "Albany", required = true)
	private String city;

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 75, message = "Must be between 1 and 75 characters")
	@ApiModelProperty(example = "New York", required = true)
	private String state;

	@NotBlank(message = "Required field")
	@Size(min = 2, max = 2, message = "Must have 2 characters")
	@ApiModelProperty(example = "NY", required = true)
	private String uf;

	@NotBlank(message = "Required field")
	@Size(min = 8, max = 8, message = "Must have 8 characters")
	@ApiModelProperty(example = "45879635", required = true)
	private String postalCode;

	@Size(min = 1, max = 75, message = "Must be between 1 and = 75 characters")
	@ApiModelProperty(example = "Floor 16", required = false)
	private String complement;

	@NotBlank(message = "Required field")
	@Size(min = 1, max = 75, message = "Must be between 1 and 75 characters")
	@ApiModelProperty(example = "McDonald's", required = true)
	private String referencePoint;
}
