package br.com.roanrobersson.rshop.domain.dto.model;

import java.time.OffsetDateTime;
import java.util.UUID;

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
@Schema(title = "Address")
@ToString(of = { "id", "nick", "main" })
public class AddressModel {

	@EqualsAndHashCode.Include
	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

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
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new AddressModelBuilder()
				.id(uuid)
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
