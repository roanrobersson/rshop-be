package br.com.roanrobersson.rshop.api.v1.model;

import java.util.UUID;

import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Privilege")
@ToString(of = { "id", "name" })
public class PrivilegeModel {

	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Size(min = 3, max = 30, message = "Must be between 3 and 30 characters")
	@Schema(example = "EDIT_CATEGORIES")
	private String name;

	@Size(min = 10, max = 100, message = "Must be between 10 and 100 characters")
	@Schema(example = "Allow edit categories")
	private String description;
}
