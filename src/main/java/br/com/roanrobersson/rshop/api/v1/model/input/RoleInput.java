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
	@Schema(example = "[5e0b121c-9f12-4fd3-a7e6-179b5007149a, 5e0b121c-9f12-4fd3-a7e6-179b5007149a]", required = true)
	private Set<PrivilegeIdInput> privileges = new HashSet<>();

	@NotBlank
	@Size(min = 3, max = 30)
	@Schema(example = "ADMIN", required = true)
	private String name;
}
