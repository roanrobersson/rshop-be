package br.com.roanrobersson.rshop.domain.dto.input.id;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.roanrobersson.rshop.core.validation.UUIDValid;
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
	@UUIDValid
	@EqualsAndHashCode.Include
	@Schema(example = "753dad79-2a1f-4f5c-bbd1-317a53587518")
	private String id;
}
