package br.com.roanrobersson.rshop.dto.response;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.roanrobersson.rshop.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private Long id;
	private Set<Long> roles = new HashSet<>();
	private Long imageId;
	private String firstName;
	private String name;
	private Instant birthDate;
	private String cpf;
	private String rg;
	private String email;
	private String password;
	private String primaryTelephone;
	private String secondaryTelephone;
	private Instant verifiedAt;
	private Instant createdAt;
	private Instant updatedAt;

	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.roles = user.getRoles().stream().map((x) -> x.getId()).collect(Collectors.toSet());
		this.firstName = user.getFirstName();
		this.name = user.getName();
		this.birthDate = user.getBirthDate();
		this.cpf = user.getCpf();
		this.rg = user.getRg();
		this.email = user.getEmail();
		this.primaryTelephone = user.getPrimaryTelephone();
		this.secondaryTelephone = user.getSecondaryTelephone();
		this.imageId = user.getImage() != null ? user.getImage().getId() : null;
		this.verifiedAt = user.getVerifiedAt();
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
	}
}
