package br.com.roanrobersson.rshop.domain.dto.model;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "anAddressModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "nick", "main" })
@Schema(title = "Address")
public class AddressModel {

	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;

	@Schema(example = "Home")
	private String nick;

	@Schema(example = "54998204476")
	private String telephone;

	@Schema(example = "342 Lake St.")
	private String addressLine;

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

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;
	
	public static AddressModelBuilder anAddressModel() {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new AddressModelBuilder()
				.id(123L)
				.nick("Casa 2")
				.telephone("54981457832")
				.addressLine("Rua Mazzoleni")
				.number("999")
				.neighborhood("Beira Lagoa")
				.city("Porto Alegre")
				.state("Rio Grande do Sul")
				.uf("RS")
				.postalCode("12345678")
				.complement("Fundos")
				.referencePoint("Próx mercado Zorzi")
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime)
				.main(true);
	}
}
