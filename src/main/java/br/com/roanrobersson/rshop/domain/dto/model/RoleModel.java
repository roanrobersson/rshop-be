package br.com.roanrobersson.rshop.domain.dto.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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
@Builder(builderMethodName = "aRoleBasicModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Schema(title = "Role")
public class RoleModel {

	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;

	@Setter(value = AccessLevel.NONE)
	@Singular(ignoreNullCollections = true)
	private Set<PrivilegeModel> privileges = new HashSet<>();

	@Schema(example = "ADMIN")
	private String name;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;

	public static RoleModelBuilder aRoleBasicModel() {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new RoleModelBuilder().id(123L).name("ROLE_ADMIN").createdAt(offsetDateTime).updatedAt(offsetDateTime);
	}
}
