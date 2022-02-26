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

	@NotBlank(message = "Required field")
	@Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
	@ApiModelProperty(example = "Kevin", required = true)
	private String firstName;

	@NotBlank(message = "Required field")
	@Size(min = 5, max = 100, message = "Must be between 5 and 100 characters")
	@ApiModelProperty(example = "Kevin Brown", required = true)
	private String name;

	@NotNull(message = "Required field")
	@Past(message = "Must be in the past")
	@AgeValid(min = 18, message = "Must have at least 18 years old")
	@ApiModelProperty(example = "1993-07-14", required = true)
	private LocalDate birthDate;

	@NotBlank(message = "Required field")
	@Size(min = 11, max = 11, message = "Must have 11 characters")
	@ApiModelProperty(example = "86213939059", required = true)
	private String cpf;

	@NotBlank(message = "Required field")
	@Size(min = 5, max = 14, message = "Must be between 1 and 14 characters")
	@ApiModelProperty(example = "355144724", required = true)
	private String rg;

	@Email
	@NotBlank(message = "Required field")
	@Size(min = 3, max = 50, message = "Must be between 3 and 50 characters")
	@ApiModelProperty(example = "kevinbrown@gmail.com", required = true)
	private String email;

	@NotBlank(message = "Required field")
	@Size(min = 8, max = 50, message = "Must be between 8 and 50 characters")
	@ApiModelProperty(example = "a3g&3Pd#", required = true)
	private String password;

	@NotBlank(message = "Required field")
	@Size(min = 10, max = 11, message = "Must be between 10 and 11 characters")
	@ApiModelProperty(example = "57991200038", required = true)
	private String primaryTelephone;

	@Size(min = 10, max = 11, message = "Must be between 10 and 11 characters")
	@ApiModelProperty(example = "54991200038", required = false)
	private String secondaryTelephone;
}
