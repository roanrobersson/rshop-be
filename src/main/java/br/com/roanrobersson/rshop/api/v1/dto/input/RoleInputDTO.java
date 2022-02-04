package br.com.roanrobersson.rshop.api.v1.dto.input;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.RoleInputValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RoleInputValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoleInput")
@ToString
public class RoleInputDTO {

	@NotEmpty(message = "Must have a set of privileges")
	@Builder.Default
	@ApiModelProperty(example = "[2, 3, 4]", required = true)
	private Set<Long> privileges = new HashSet<>();

	@NotBlank(message = "Required field")
	@Size(min = 3, max = 30, message = "Must be between 3 and 30 characters")
	@ApiModelProperty(example = "ADMIN", required = true)
	private String name;
}
