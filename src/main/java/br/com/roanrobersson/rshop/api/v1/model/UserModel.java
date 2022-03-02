package br.com.roanrobersson.rshop.api.v1.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "User")
@ToString(of = { "id", "firstName" })
public class UserModel {

	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@Schema(example = "[2, 4, 6]")
	private Set<UUID> roles = new HashSet<>();

	@Builder.Default
	@Schema(example = "[4, 5, 6, 9, 13, 34]")
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
