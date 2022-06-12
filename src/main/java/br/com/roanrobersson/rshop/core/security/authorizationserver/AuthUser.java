package br.com.roanrobersson.rshop.core.security.authorizationserver;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import br.com.roanrobersson.rshop.domain.model.User;
import lombok.Getter;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private UUID userId;
	private String fullName;

	public AuthUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);

		this.userId = user.getId();
		this.fullName = user.getName();
	}

}
