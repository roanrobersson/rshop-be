package br.com.roanrobersson.rshop.dto.user;

import br.com.roanrobersson.rshop.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserResponseDTO extends AbstractUserDTO {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String email;
	
	public UserResponseDTO(Long id, String firstName, String lastName, String email,
			String primaryPhone, String secondaryPhone) {
		super(firstName, lastName, primaryPhone, secondaryPhone);
		this.id = id;
		this.email = email;
	}
	
	public UserResponseDTO(User entity) {
		super(entity);
		this.id = entity.getId();
		this.email = entity.getEmail();
	}
}
