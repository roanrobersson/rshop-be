package br.com.roanrobersson.rshop.api.v1.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
	@Builder.Default
	private Set<Long> rolesIds = new HashSet<>();
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
}
