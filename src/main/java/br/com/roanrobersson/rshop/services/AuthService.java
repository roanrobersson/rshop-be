package br.com.roanrobersson.rshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.entities.User;
import br.com.roanrobersson.rshop.repositories.UserRepository;
import br.com.roanrobersson.rshop.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	public Long getUserId() {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			return repository.findByEmail(email).get().getId();
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid user");
		}
	}

	@Transactional(readOnly = true)
	public User getAuthenticatedUser() {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			return repository.findByEmail(email).get();
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid user");
		}
	}

	public boolean authenticatedUserIdEquals(Long userId) {
		return getUserId().equals(userId);
	}
}
