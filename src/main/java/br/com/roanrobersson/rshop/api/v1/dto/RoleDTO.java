package br.com.roanrobersson.rshop.api.v1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.RoleValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RoleValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoleRequest")
@ToString
public class RoleDTO {
	
	@ApiModelProperty(hidden = true)
	private Long id;
	
	@NotBlank(message = "Required field")
	@Size(min = 3, max = 30, message = "Must be between 3 and 30 characters")
	@ApiModelProperty(example = "ADMIN", required = true)
	private String name;
}
