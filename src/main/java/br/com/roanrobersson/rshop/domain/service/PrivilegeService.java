package br.com.roanrobersson.rshop.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.repository.PrivilegeRepository;

@Service
public class PrivilegeService {

	@Autowired
	private PrivilegeRepository repository;
	
	@Transactional(readOnly = true)
	public Privilege findById(Long privilegeId) {
		return findPrivilegeOrThrow(privilegeId);
	}
	
	private Privilege findPrivilegeOrThrow(Long privilegeId) {
		return repository.findById(privilegeId)
				.orElseThrow(() -> new ResourceNotFoundException("Privilege" + privilegeId + "not found"));
	}
}
