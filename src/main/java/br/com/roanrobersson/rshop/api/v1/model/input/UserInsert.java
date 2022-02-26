package br.com.roanrobersson.rshop.api.v1.model.input;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.core.validation.AgeValid;
import br.com.roanrobersson.rshop.core.validation.UserInsertValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@UserInsertValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserInsert")
@ToString(of = { "firstName" })
public class UserInsert {

	@NotBlank
	@Size(min = 2, max = 50)
	@ApiModelProperty(example = "Kevin", required = true)
	private String firstName;

	@NotBlank
	@Size(min = 5, max = 100)
	@ApiModelProperty(example = "Kevin Brown", required = true)
	private String name;

	@NotNull
	@Past
	@AgeValid(min = 18)
	@ApiModelProperty(example = "1993-07-14", required = true)
	private LocalDate birthDate;

	@NotBlank
	@Size(min = 11, max = 11)
	@ApiModelProperty(example = "86213939059", required = true)
	private String cpf;

	@NotBlank
	@Size(min = 5, max = 14)
	@ApiModelProperty(example = "355144724", required = true)
	private String rg;

	@Email
	@NotBlank
	@Size(min = 3, max = 50)
	@ApiModelProperty(example = "kevinbrown@gmail.com", required = true)
	private String email;

	@NotBlank
	@Size(min = 8, max = 50)
	@ApiModelProperty(example = "a3g&3Pd#", required = true)
	private String password;

	@NotBlank
	@Size(min = 10, max = 11)
	@ApiModelProperty(example = "57991200038", required = true)
	private String primaryTelephone;

	@Size(min = 10, max = 11)
	@ApiModelProperty(example = "54991200038", required = false)
	private String secondaryTelephone;
}
