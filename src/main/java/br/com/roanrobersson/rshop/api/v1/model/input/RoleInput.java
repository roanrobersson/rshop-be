package br.com.roanrobersson.rshop.api.v1.model.input;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.api.v1.model.input.id.PrivilegeIdInput;
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
@Schema(title = "RoleInput")
@ToString
public class RoleInput {

	@Valid
	@NotEmpty
	@Builder.Default
	@Schema(example = "[\"91f550d9-548f-4d09-ac9c-1a95219033f7\", \"b7705487-51a1-4092-8b62-91dccd76a41a\"]", required = true)
	private Set<PrivilegeIdInput> privileges = new HashSet<>();

	@NotBlank
	@Size(min = 3, max = 30)
	@Schema(example = "ADMIN", required = true)
	private String name;
}
