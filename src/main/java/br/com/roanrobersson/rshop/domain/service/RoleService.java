package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.dto.input.RoleInput;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.RoleNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;

@Service
public class RoleService {

	private static final String MSG_ROLE_IN_USE = "Role with ID %s cannot be removed, because it is in use";
	private static final String MSG_ROLE_ALREADY_EXISTS = "There is already a role registered with that name %s";

	@Autowired
	private RoleRepository repository;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private RoleMapper mapper;

	@Transactional(readOnly = true)
	public Page<Role> list(Pageable pageable) {
		Page<Role> roles = repository.findAll(pageable);
		repository.findRolesWithPrivileges(roles.toList());
		return roles;
	}

	@Transactional(readOnly = true)
	public Role findById(Long roleId) {
		Role role = repository.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
		role.getPrivileges().size(); // Force fetch privileges
		return role;
	}

	@Transactional
	public Role insert(RoleInput roleInput) {
		validateUniqueInsert(roleInput);
		Role role = mapper.toRole(roleInput);
		role = repository.save(role);
		return role;
	}

	@Transactional
	public Role update(Long roleId, RoleInput roleInput) {
		validateUniqueUpdate(roleId, roleInput);
		Role role = findById(roleId);
		mapper.update(roleInput, role);
		return repository.save(role);
	}

	public void delete(Long roleId) {
		try {
			repository.deleteById(roleId);
		} catch (EmptyResultDataAccessException e) {
			throw new RoleNotFoundException(roleId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ROLE_IN_USE, roleId));
		}
	}

	@Transactional(readOnly = true)
	public Long count() {
		return repository.count();
	}

	@Transactional(readOnly = true)
	public Set<Privilege> getPrivileges(Long roleId) {
		return findById(roleId).getPrivileges();
	}

	@Transactional
	public void linkPrivilege(Long roleId, Long privilegeId) {
		Role role = findById(roleId);
		Privilege privilege = privilegeService.findById(privilegeId);
		role.getPrivileges().add(privilege);
		repository.save(role);
	}

	@Transactional
	public void unlinkPrivilege(Long roleId, Long privilegeId) {
		Role role = findById(roleId);
		Privilege privilege = privilegeService.findById(privilegeId);
		role.getPrivileges().remove(privilege);
		repository.save(role);
	}

	private void validateUniqueInsert(RoleInput roleInput) {
		Optional<Role> optional = repository.findByName(roleInput.getName());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_ROLE_ALREADY_EXISTS, roleInput.getName()));
		}
	}

	private void validateUniqueUpdate(Long roleId, RoleInput roleInput) {
		Optional<Role> optional = repository.findByName(roleInput.getName());
		if (optional.isPresent() && !optional.get().getId().equals(roleId)) {
			throw new UniqueException(String.format(MSG_ROLE_ALREADY_EXISTS, roleInput.getName()));
		}
	}
}
