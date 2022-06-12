package br.com.roanrobersson.rshop.domain.dto.input;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.AgeValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(title = "UserUpdate")
@ToString(of = { "firstName" })
public class UserUpdate {

	@NotBlank
	@Size(min = 2, max = 50)
	@Schema(example = "Kevin", required = true)
	private String firstName;

	@NotBlank
	@Size(min = 5, max = 100)
	@Schema(example = "Kevin Brown", required = true)
	private String name;

	@NotNull
	@Past
	@AgeValid(min = 18)
	@Schema(example = "1993-07-14", required = true)
	private LocalDate birthDate;

	@NotBlank
	@Size(min = 11, max = 11)
	@Schema(example = "86213939059", required = true)
	private String cpf;

	@NotBlank
	@Size(min = 5, max = 14)
	@Schema(example = "355144724", required = true)
	private String rg;

	@NotBlank
	@Size(min = 10, max = 11)
	@Schema(example = "57991200038", required = true)
	private String primaryTelephone;

	@Size(min = 10, max = 11)
	@Schema(example = "54991200038", required = false)
	private String secondaryTelephone;
}
