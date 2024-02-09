package br.com.roanrobersson.rshop.domain.dto.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.Size;

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
@Builder(builderMethodName = "aPrivilegeModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "id", "name" })
@Schema(title = "Privilege")
public class PrivilegeModel {

	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;

	@Size(min = 3, max = 30, message = "Must be between 3 and 30 characters")
	@Schema(example = "EDIT_CATEGORIES")
	private String name;

	@Size(min = 10, max = 100, message = "Must be between 10 and 100 characters")
	@Schema(example = "Allow edit categories")
	private String description;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;

	public static PrivilegeModelBuilder aPrivilegeModel() {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new PrivilegeModelBuilder()
				.id(123L)
				.name("EDIT_CATEGORIES")
				.description("Allow edit categories")
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
