package br.com.roanrobersson.rshop.domain.dto.model;

import java.util.UUID;

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
@Schema(title = "Privilege")
@ToString(of = { "id", "name" })
public class PrivilegeModel {

	@EqualsAndHashCode.Include
	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Size(min = 3, max = 30, message = "Must be between 3 and 30 characters")
	@Schema(example = "EDIT_CATEGORIES")
	private String name;

	@Size(min = 10, max = 100, message = "Must be between 10 and 100 characters")
	@Schema(example = "Allow edit categories")
	private String description;

	public static PrivilegeModelBuilder aPrivilegeModel() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		return new PrivilegeModelBuilder().id(uuid).name("EDIT_CATEGORIES").description("Allow edit categories");
	}
}
