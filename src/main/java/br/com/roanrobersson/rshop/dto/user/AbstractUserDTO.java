package br.com.roanrobersson.rshop.dto.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.User;

public abstract class AbstractUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 2, max = 50, message = "Deve ter entre 2 e 50 caracteres")
	private String firstName;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 2, max = 50, message = "Deve ter entre 2 e 50 caracteres")
	private String lastName;
	
	public AbstractUserDTO() {
	}
	
	public AbstractUserDTO(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public AbstractUserDTO(User entity) {
		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
