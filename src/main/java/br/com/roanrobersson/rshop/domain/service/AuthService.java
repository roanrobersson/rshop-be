package br.com.roanrobersson.rshop.domain.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.exception.UnauthorizedException;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	public UUID getUserId() {
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

	public boolean authenticatedUserIdEquals(UUID userId) {
		return getUserId().equals(userId);
	}
}
