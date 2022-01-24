package br.com.roanrobersson.rshop.api.v1.dto;

import javax.validation.constraints.NotEmpty;
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
@ApiModel(value = "UserChangePasswordRequest")
@ToString
public class UserChangePasswordDTO {

	@NotEmpty(message = "Required field")
	@Size(min = 8, max = 50, message = "Must be between 8 and 60 characters")
	@ApiModelProperty(example = "a3g&3Pd#", required = true)
	private String newPassword;
}
