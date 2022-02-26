package br.com.roanrobersson.rshop.api.v1.model.input;

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
@ApiModel(value = "UserChangePasswordInput")
@ToString
public class UserChangePasswordInput {

	@NotEmpty
	@Size(min = 8, max = 50)
	@ApiModelProperty(example = "a3g&3Pd#", required = true)
	private String newPassword;
}
