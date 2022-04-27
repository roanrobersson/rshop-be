package br.com.roanrobersson.rshop.api.v1.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(title = "User")
@ToString(of = { "id", "firstName" })
public class UserModel {

	@EqualsAndHashCode.Include
	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@Schema(example = "[18aace1e-f36a-4d71-b4d1-124387d9b63a, eb1ffb79-5dfb-4b13-b615-eae094a06207]")
	private Set<UUID> roles = new HashSet<>();

	@Builder.Default
	@Setter(value = AccessLevel.NONE)
	@Schema(example = "[b7705487-51a1-4092-8b62-91dccd76a41a, 91f550d9-548f-4d09-ac9c-1a95219033f7, "
			+ "ab7fab73-0464-4f7c-bc18-069ff63a3dc9, bafcfedf-8f1c-4f16-b474-351e347b13de")
	private Set<UUID> privileges = new HashSet<>();

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
	private Instant verifiedAt;

	@Schema(example = "2013-03-13 05:11:00")
	private Instant lastLoginAt;
}
