package br.com.roanrobersson.rshop.domain.dto.input;

import javax.validation.constraints.NotBlank;
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
@Builder(builderMethodName = "aPrivilegeInput", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(title = "PrivilegeInput")
@ToString
public class PrivilegeInput {

	@NotBlank
	@Size(min = 3, max = 30)
	@Schema(example = "EDIT_CATEGORIES", required = true)
	@EqualsAndHashCode.Include
	private String name;

	@NotBlank
	@Size(min = 10, max = 100)
	@Schema(example = "Allow edit categories", required = true)
	private String description;

	public static PrivilegeInputBuilder aPrivilegeInput() {
		return new PrivilegeInputBuilder().name("EDIT_CATEGORIES").description("Allow edit categories");
	}
}
