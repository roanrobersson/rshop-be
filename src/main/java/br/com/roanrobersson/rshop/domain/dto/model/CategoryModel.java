package br.com.roanrobersson.rshop.domain.dto.model;

import java.util.UUID;

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
@Builder(builderMethodName = "anCategoryModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(title = "Category")
@ToString
public class CategoryModel {

	@EqualsAndHashCode.Include
	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Schema(example = "Cleaning")
	private String name;

	public static CategoryModelBuilder anCategoryModel() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		return new CategoryModelBuilder().id(uuid).name("Electronic");
	}
}
