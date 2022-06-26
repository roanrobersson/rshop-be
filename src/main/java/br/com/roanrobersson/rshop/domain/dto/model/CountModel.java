package br.com.roanrobersson.rshop.domain.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Schema(title = "Category")
public class CountModel {

	@EqualsAndHashCode.Include
	@Schema(example = "55")
	private Long count;
}
