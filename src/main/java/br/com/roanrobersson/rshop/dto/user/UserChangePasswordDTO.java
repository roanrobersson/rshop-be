package br.com.roanrobersson.rshop.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserChangePasswordDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Campo obrigatório")
	@Size(min = 8, max = 50, message = "Deve ter entre 8 e 50 caracteres")
	private String newPassword;
	
	public UserChangePasswordDTO() {
	}
	
	public UserChangePasswordDTO(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
