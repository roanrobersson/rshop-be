package br.com.roanrobersson.rshop.domain.dto.model;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aRoleBasicModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Schema(title = "Role")
public class RoleBasicModel {

	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;

	@Schema(example = "ADMIN")
	private String name;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;

	public static RoleBasicModelBuilder aRoleBasicModel() {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new RoleBasicModelBuilder().id(123L).name("ROLE_ADMIN").createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
