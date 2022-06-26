package br.com.roanrobersson.rshop.domain.dto.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(title = "UserChangePasswordInput")
public class UserChangePasswordInput {

	@NotEmpty
	@Size(min = 8, max = 50)
	@Schema(example = "a3g&3Pd#", required = true)
	private String newPassword;
}
