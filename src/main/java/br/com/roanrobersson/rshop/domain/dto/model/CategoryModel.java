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
@Builder(builderMethodName = "anCategoryModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Schema(title = "Category")
public class CategoryModel {

	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;

	@Schema(example = "Cleaning")
	private String name;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;

	public static CategoryModelBuilder anCategoryModel() {
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new CategoryModelBuilder()
				.id(123L)
				.name("Electronic")
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
