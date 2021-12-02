package br.com.roanrobersson.rshop.dto.response;

import java.time.Instant;

import br.com.roanrobersson.rshop.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {

	private Long id;
	private String name;
	private Instant createdAt;
	private Instant updatedAt;

	public RoleResponseDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.createdAt = role.getCreatedAt();
		this.updatedAt = role.getUpdatedAt();
	}
}
