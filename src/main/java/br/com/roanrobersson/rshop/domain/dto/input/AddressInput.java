package br.com.roanrobersson.rshop.domain.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(title = "AddressInput")
@ToString(of = { "nick" })
public class AddressInput {

	@NotBlank
	@Size(min = 1, max = 20)
	@Schema(example = "Home", required = true)
	private String nick;

	@NotBlank
	@Size(min = 10, max = 11)
	@Schema(example = "54998204476", required = true)
	private String telephone;

	@NotBlank
	@Size(min = 1, max = 75)
	@Schema(example = "342 Lake St.", required = true)
	private String address;

	@NotBlank
	@Size(min = 1, max = 6)
	@Schema(example = "11228", required = true)
	private String number;

	@NotBlank
	@Size(min = 1, max = 30)
	@Schema(example = "Arbor Hill", required = true)
	private String neighborhood;

	@NotBlank
	@Size(min = 1, max = 75)
	@Schema(example = "Albany", required = true)
	private String city;

	@NotBlank
	@Size(min = 1, max = 75)
	@Schema(example = "New York", required = true)
	private String state;

	@NotBlank
	@Size(min = 2, max = 2)
	@Schema(example = "NY", required = true)
	private String uf;

	@NotBlank
	@Size(min = 8, max = 8)
	@Schema(example = "45879635", required = true)
	private String postalCode;

	@Size(min = 1, max = 75)
	@Schema(example = "Floor 16", required = false)
	private String complement;

	@NotBlank
	@Size(min = 1, max = 75)
	@Schema(example = "McDonald's", required = true)
	private String referencePoint;
}
