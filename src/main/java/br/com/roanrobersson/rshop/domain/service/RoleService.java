package br.com.roanrobersson.rshop.domain.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.RoleNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;

@Service
public class RoleService {

	private static final String MSG_ROLE_IN_USE = "Role with ID %s cannot be removed, because it is in use";

	@Autowired
	private RoleRepository repository;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private RoleMapper mapper;

	@Transactional(readOnly = true)
	public List<Role> findAll(Sort sort) {
		List<Role> roles = repository.findAll(sort);
		repository.findRolesWithPrivileges(roles);
		return roles;
	}

	@Transactional(readOnly = true)
	public Role findById(UUID roleId) {
		return repository.findByIdWithPrivileges(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
	}

	@Transactional
	public Role insert(RoleInputDTO roleInputDTO) {
		Role role = mapper.toRole(roleInputDTO);
		role = repository.save(role);
		return role;
	}

	@Transactional
	public Role update(UUID roleId, RoleInputDTO roleInputDTO) {
		Role role = findById(roleId);
		mapper.update(roleInputDTO, role);
		return repository.save(role);
	}

	public void delete(UUID roleId) {
		try {
			repository.deleteById(roleId);
		} catch (EmptyResultDataAccessException e) {
			throw new RoleNotFoundException(roleId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ROLE_IN_USE, roleId));
		}
	}

	@Transactional(readOnly = true)
	public Set<Privilege> getPrivileges(UUID roleId) {
		return findById(roleId).getPrivileges();
	}

	@Transactional
	public void linkPrivilege(UUID roleId, UUID privilegeId) {
		Role role = findById(roleId);
		Privilege privilege = privilegeService.findById(privilegeId);
		role.getPrivileges().add(privilege);
		repository.save(role);
	}

	@Transactional
	public void unlinkPrivilege(UUID roleId, UUID privilegeId) {
		Role role = findById(roleId);
		Privilege privilege = privilegeService.findById(privilegeId);
		role.getPrivileges().remove(privilege);
		repository.save(role);
	}
}
