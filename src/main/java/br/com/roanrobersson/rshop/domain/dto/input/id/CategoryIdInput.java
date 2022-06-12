package br.com.roanrobersson.rshop.domain.dto.input.id;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.roanrobersson.rshop.core.validation.UUIDValid;
import br.com.roanrobersson.rshop.domain.dto.input.id.serializer.CategoryIdInputSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@JsonSerialize(using = CategoryIdInputSerializer.class)
public class CategoryIdInput {

	@NotNull
	@UUIDValid
	@EqualsAndHashCode.Include
	@Schema(example = "753dad79-2a1f-4f5c-bbd1-317a53587518")
	private String id;
}
