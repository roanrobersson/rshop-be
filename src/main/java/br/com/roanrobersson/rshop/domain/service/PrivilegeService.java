package br.com.roanrobersson.rshop.domain.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.exception.PrivilegeNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.repository.PrivilegeRepository;

@Service
public class PrivilegeService {

	@Autowired
	private PrivilegeRepository repository;

	@Transactional(readOnly = true)
	public Page<Privilege> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Privilege findById(UUID privilegeId) {
		return repository.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException(privilegeId));
	}

	@Transactional(readOnly = true)
	public Long count() {
		return repository.count();
	}
}
