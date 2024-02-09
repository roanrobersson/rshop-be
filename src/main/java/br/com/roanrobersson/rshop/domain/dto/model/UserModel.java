package br.com.roanrobersson.rshop.domain.dto.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "anUserModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "firstName" })
@Schema(title = "User")
public class UserModel {

	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;

	@Setter(value = AccessLevel.NONE)
	@Singular(ignoreNullCollections = true)
	private Set<RoleBasicModel> roles = new HashSet<>();

	@Setter(value = AccessLevel.NONE)
	@Singular(ignoreNullCollections = true)
	private Set<AddressModel> addresses = new HashSet<>();

	@Schema(example = "Kevin", required = true)
	private String firstName;

	@Schema(example = "Kevin Brown", required = true)
	private String name;

	@Schema(example = "1993-07-14")
	private LocalDate birthDate;

	@Schema(example = "86213939059")
	private String cpf;

	@Schema(example = "355144724")
	private String rg;

	@Schema(example = "kevinbrown@gmail.com")
	private String email;

	@Schema(example = "57991200038")
	private String primaryTelephone;

	@Schema(example = "54991200038")
	private String secondaryTelephone;

	@Schema(example = "2013-03-13 05:11:00")
	private OffsetDateTime verifiedAt;

	@Schema(example = "2013-03-13 05:11:00")
	private OffsetDateTime lastLoginAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;

	public static UserModelBuilder anUserModel() {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new UserModelBuilder()
				.id(123L)
				.firstName("Madalena")
				.name("Madalena Bernardon")
				.birthDate(LocalDate.parse("1993-01-16"))
				.rg("222182428")
				.cpf("67709960065")
				.email("madalenabernardon@gmail.com")
				.primaryTelephone("54998223654")
				.secondaryTelephone("5433417898")
				.verifiedAt(offsetDateTime)
				.lastLoginAt(offsetDateTime)
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
