package br.com.roanrobersson.rshop.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	public List<Privilege> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Transactional(readOnly = true)
	public Privilege findById(UUID privilegeId) {
		return repository.findById(privilegeId).orElseThrow(() -> new PrivilegeNotFoundException(privilegeId));
	}
}
