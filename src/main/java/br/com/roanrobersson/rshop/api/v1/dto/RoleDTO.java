package br.com.roanrobersson.rshop.api.v1.dto;

import java.util.HashSet;
import java.util.Set;

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
@ApiModel(value = "Role")
@ToString
public class RoleDTO {

	@ApiModelProperty(example = "22")
	private Long id;

	@Builder.Default
	@ApiModelProperty(example = "[2, 3, 4]")
	private Set<Long> privileges = new HashSet<>();

	@ApiModelProperty(example = "ADMIN")
	private String name;
}
