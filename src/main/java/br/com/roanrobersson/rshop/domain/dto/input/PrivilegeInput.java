package br.com.roanrobersson.rshop.domain.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(title = "PrivilegeInput")
@ToString
public class PrivilegeInput {

	@NotBlank
	@Size(min = 3, max = 30)
	@Schema(example = "EDIT_CATEGORIES", required = true)
	private String name;

	@NotBlank
	@Size(min = 10, max = 100)
	@Schema(example = "Allow edit categories", required = true)
	private String description;
}
