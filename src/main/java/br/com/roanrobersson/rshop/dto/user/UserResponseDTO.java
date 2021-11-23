package br.com.roanrobersson.rshop.dto.user;

import br.com.roanrobersson.rshop.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserResponseDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	public UserResponseDTO(Long id, String firstName, String lastName, String email) {
		super(id, firstName, lastName);
		this.email = email;
	}
	
	public UserResponseDTO(User entity) {
		super(entity);
		this.email = entity.getEmail();
	}
}
