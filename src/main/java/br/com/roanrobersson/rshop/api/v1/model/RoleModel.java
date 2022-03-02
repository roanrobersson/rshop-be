package br.com.roanrobersson.rshop.api.v1.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import br.com.roanrobersson.rshop.domain.validation.RoleInputValid;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "Role")
@ToString
public class RoleModel {

	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@Schema(example = "[2, 3, 4]")
	private Set<UUID> privileges = new HashSet<>();

	@Schema(example = "ADMIN")
	private String name;
}
