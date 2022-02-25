package br.com.roanrobersson.rshop.api.v1.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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
@ApiModel(value = "User")
@ToString(of = { "id", "firstName" })
public class UserModel {

	@ApiModelProperty(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@ApiModelProperty(example = "[2, 4, 6]")
	private Set<UUID> roles = new HashSet<>();

	@Builder.Default
	@ApiModelProperty(example = "[4, 5, 6, 9, 13, 34]")
	private Set<UUID> privileges = new HashSet<>();

	@ApiModelProperty(example = "Kevin", required = true)
	private String firstName;

	@ApiModelProperty(example = "Kevin Brown", required = true)
	private String name;

	@ApiModelProperty(example = "1993-07-14")
	private LocalDate birthDate;

	@ApiModelProperty(example = "86213939059")
	private String cpf;

	@ApiModelProperty(example = "355144724")
	private String rg;

	@ApiModelProperty(example = "kevinbrown@gmail.com")
	private String email;

	@ApiModelProperty(example = "57991200038")
	private String primaryTelephone;

	@ApiModelProperty(example = "54991200038")
	private String secondaryTelephone;

	@ApiModelProperty(example = "2013-03-13 05:11:00")
	private Instant verifiedAt;

	@ApiModelProperty(example = "2013-03-13 05:11:00")
	private Instant lastLoginAt;
}
