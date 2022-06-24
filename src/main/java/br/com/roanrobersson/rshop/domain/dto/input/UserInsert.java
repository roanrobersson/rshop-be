package br.com.roanrobersson.rshop.domain.dto.input;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.AgeValid;
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
@Builder(builderMethodName = "anUserInsert", toBuilder = true)
@EqualsAndHashCode
@Schema(title = "UserInsert")
@ToString(of = { "firstName" })
public class UserInsert {

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

	@Email
	@NotBlank
	@Size(min = 3, max = 50)
	@Schema(example = "kevinbrown@gmail.com", required = true)
	@EqualsAndHashCode.Include
	private String email;

	@NotBlank
	@Size(min = 8, max = 50)
	@Schema(example = "a3g&3Pd#", required = true)
	private String password;

	@NotBlank
	@Size(min = 10, max = 11)
	@Schema(example = "57991200038", required = true)
	private String primaryTelephone;

	@Size(min = 10, max = 11)
	@Schema(example = "54991200038", required = false)
	private String secondaryTelephone;

	public static UserInsertBuilder anUserInsert() {
		return new UserInsertBuilder()
				.firstName("Madalena")
				.name("Madalena Bernardon")
				.birthDate(LocalDate.parse("1993-01-16"))
				.rg("222182428")
				.cpf("67709960065")
				.email("madalenabernardon@gmail.com")
				.password("12345678")
				.primaryTelephone("54998223654")
				.secondaryTelephone("5433417898");
	}
}
