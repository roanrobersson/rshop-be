package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.PrivilegeBuilder.aPrivilege;
import static br.com.roanrobersson.rshop.builder.RoleBuilder.aRole;
import static br.com.roanrobersson.rshop.builder.UserBuilder.EXISTING_EMAIL;
import static br.com.roanrobersson.rshop.builder.UserBuilder.anUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.core.security.authorizationserver.AuthUserDetailsService;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class AuthUserDetailsServiceUT {

	@InjectMocks
	private AuthUserDetailsService service;

	@Mock
	private UserRepository repository;

	@Test
	void loadUserByUsername_ReturnUserDetails_IdExist() {
		User user = anUser().withRole(aRole().withPrivilege(aPrivilege().build()).build()).build();
		when(repository.findByEmailWithRolesAndPrivileges(EXISTING_EMAIL)).thenReturn(Optional.of(user));

		UserDetails result = service.loadUserByUsername(EXISTING_EMAIL);

		assertNotNull(result);
		assertEquals(user.getEmail(), result.getUsername());
		assertEquals(user.getPassword(), result.getPassword());
		verify(repository, times(1)).findByEmailWithRolesAndPrivileges(EXISTING_EMAIL);
	}

	@Test
	void loadUserByUsername_ThrowUsernameNotFoundException_EmailDoesNotExist() {
		when(repository.findByEmailWithRolesAndPrivileges(EXISTING_EMAIL)).thenReturn(Optional.empty());

		assertThrowsExactly(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(EXISTING_EMAIL);
		});

		verify(repository, times(1)).findByEmailWithRolesAndPrivileges(EXISTING_EMAIL);
	}
}
