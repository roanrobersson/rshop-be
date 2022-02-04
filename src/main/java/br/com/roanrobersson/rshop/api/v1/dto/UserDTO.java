package br.com.roanrobersson.rshop.api.v1.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

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
public class UserDTO {

	@ApiModelProperty(example = "44")
	private Long id;

	@Builder.Default
	@ApiModelProperty(example = "[2, 4, 6]")
	private Set<Long> roles = new HashSet<>();

	@Builder.Default
	@ApiModelProperty(example = "[4, 5, 6, 9, 13, 34]")
	private Set<Long> privileges = new HashSet<>();

	@Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
	private String firstName;

	private String name;

	@ApiModelProperty(example = "1993-07-14 07:00:00")
	private Instant birthDate;

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
}
