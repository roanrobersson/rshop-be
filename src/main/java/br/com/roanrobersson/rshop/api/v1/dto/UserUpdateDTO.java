package br.com.roanrobersson.rshop.api.v1.dto;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
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
@ApiModel(value = "UserUpdateRequest")
@ToString(of = {"firstName"})
public class UserUpdateDTO {

	@Positive
	@ApiModelProperty(example = "22102", required = false)
	public Long imageId;

	@NotBlank(message = "Required field")
	@Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
	@ApiModelProperty(example = "Kevin", required = true)
	private String firstName;

	@NotBlank(message = "Required field")
	@Size(min = 5, max = 100, message = "Must be between 5 and 100 characters")
	@ApiModelProperty(example = "Kevin Brown", required = true)
	private String name;

	@Past(message = "Must be in the past")
	@ApiModelProperty(example = "1993-07-14 07:00:00", required = true)
	private Instant birthDate;

	@NotBlank(message = "Required field")
	@Size(min = 11, max = 11, message = "Must have 11 characters")
	@ApiModelProperty(example = "86213939059", required = true)
	private String cpf;

	@NotBlank(message = "Required field")
	@Size(min = 5, max = 14, message = "Must be between 1 and 14 characters")
	@ApiModelProperty(example = "355144724", required = true)
	private String rg;
	
	@NotBlank(message = "Required field")
	@Size(min = 10, max = 11, message = "Must be between 10 and 11 characters")
	@ApiModelProperty(example = "57991200038", required = true)
	private String primaryTelephone;

	@Size(min = 10, max = 11, message = "Must be between 10 and 11 characters")
	@ApiModelProperty(example = "54991200038", required = false)
	private String secondaryTelephone;
}
