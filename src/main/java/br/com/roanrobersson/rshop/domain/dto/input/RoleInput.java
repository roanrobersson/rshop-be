package br.com.roanrobersson.rshop.domain.dto.input;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.domain.dto.input.id.PrivilegeIdInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aRoleInput", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Schema(title = "RoleInput")
public class RoleInput {

	@Valid
	@NotEmpty
	@Setter(value = AccessLevel.NONE)
	@Singular(ignoreNullCollections = true)
	@Schema(example = "[\"91f550d9-548f-4d09-ac9c-1a95219033f7\", \"b7705487-51a1-4092-8b62-91dccd76a41a\"]", required = true)
	private Set<PrivilegeIdInput> privileges = new HashSet<>();

	@NotBlank
	@Size(min = 3, max = 30)
	@EqualsAndHashCode.Include
	@Schema(example = "ADMIN", required = true)
	private String name;

	public static RoleInputBuilder aRoleInput() {
		return new RoleInputBuilder().name("ROLE_ADMIN");
	}
}
