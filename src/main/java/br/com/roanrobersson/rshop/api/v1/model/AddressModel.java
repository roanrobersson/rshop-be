package br.com.roanrobersson.rshop.api.v1.model;

import java.util.UUID;

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
@Schema(title = "Address")
@ToString(of = { "id", "nick", "main" })
public class AddressModel {

	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Schema(example = "Home")
	private String nick;

	@Schema(example = "54998204476")
	private String telephone;

	@Schema(example = "342 Lake St.")
	private String address;

	@Schema(example = "11228")
	private String number;

	@Schema(example = "Arbor Hill")
	private String neighborhood;

	@Schema(example = "Albany")
	private String city;

	@Schema(example = "New York")
	private String state;

	@Schema(example = "NY")
	private String uf;

	@Schema(example = "45879635")
	private String postalCode;

	@Schema(example = "Floor 16")
	private String complement;

	@Schema(example = "McDonald's")
	private String referencePoint;

	@Schema(example = "true")
	private Boolean main;
}
