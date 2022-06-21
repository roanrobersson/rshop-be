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
@Builder(builderMethodName = "aCategoryInput", toBuilder = true)
@EqualsAndHashCode
@ToString
@Schema(title = "CategoryInput")
public class CategoryInput {

	@NotBlank
	@Size(min = 3, max = 127)
	@Schema(example = "Cleaning", required = true)
	@EqualsAndHashCode.Include
	private String name;

	public static CategoryInputBuilder aCategoryInput() {
		return new CategoryInputBuilder().name("Electronic");
	}
}
