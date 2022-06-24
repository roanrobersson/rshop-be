package br.com.roanrobersson.rshop.core.security.authorizationserver;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository
				.findByEmailWithRolesAndPrivileges(email)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return new AuthUser(user, getAuthorities(user));
	}

	private Collection<GrantedAuthority> getAuthorities(User user) {
		return user
				.getRoles()
				.stream()
				.flatMap(role -> role.getPrivileges().stream())
				.map(privilege -> new SimpleGrantedAuthority(privilege.getName().toUpperCase()))
				.collect(Collectors.toSet());
	}
}
