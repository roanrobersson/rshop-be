package br.com.roanrobersson.rshop.domain.dto.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aRoleModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(title = "Role")
@ToString
public class RoleModel {

	@EqualsAndHashCode.Include
	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@Setter(value = AccessLevel.NONE)
	@Schema(example = "[b7705487-51a1-4092-8b62-91dccd76a41a, 91f550d9-548f-4d09-ac9c-1a95219033f7"
			+ "ab7fab73-0464-4f7c-bc18-069ff63a3dc9, bafcfedf-8f1c-4f16-b474-351e347b13de]")
	private Set<UUID> privileges = new HashSet<>();

	@Schema(example = "ADMIN")
	private String name;

	public static RoleModelBuilder aRoleModel() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		return new RoleModelBuilder().id(uuid).name("ROLE_ADMIN");
	}
}
