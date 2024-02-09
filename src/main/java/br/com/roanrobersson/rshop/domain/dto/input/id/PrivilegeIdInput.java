package br.com.roanrobersson.rshop.domain.dto.input.id;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.roanrobersson.rshop.domain.dto.input.id.serializer.PrivilegeIdInputSerializer;
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
@Builder(builderMethodName = "anPrivilegeIdInput", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@JsonSerialize(using = PrivilegeIdInputSerializer.class)
public class PrivilegeIdInput {

	@NotNull
	@EqualsAndHashCode.Include
	@Schema(example = "123")
	private Long id;
}
