package br.com.roanrobersson.rshop.domain.dto.input.id;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.roanrobersson.rshop.domain.dto.input.id.serializer.CategoryIdInputSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "anCategoryIdInput", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@JsonSerialize(using = CategoryIdInputSerializer.class)
public class CategoryIdInput {

	@NotNull
	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;
}
