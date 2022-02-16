package br.com.roanrobersson.rshop.domain.listener;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Component
public class AuthenticationListener {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationListener.class);

	@Autowired
	private UserRepository repository;

	@EventListener
	public void authSuccessEventListener(AuthenticationSuccessEvent event) {
		// Ignore if is authorization event
		if (event.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		// Ignore in tests
		if (!(event.getAuthentication().getPrincipal() instanceof User)) {
			return;
		}
		User user = (User) event.getAuthentication().getPrincipal();
		user.setLastLoginAt(Instant.now());
		repository.save(user);
		logger.info(String.format("User %s has been authenticated", user.getUsername()));
	}

	@EventListener
	public void authFailedEventListener(AbstractAuthenticationFailureEvent event) {
		// Ignore if is authorization event
		if (event.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		logger.warn(String.format("Error authenticating %s", event.getAuthentication().getPrincipal()));
	}
}
