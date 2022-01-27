package br.com.roanrobersson.rshop.domain.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.domain.Privilege;
import br.com.roanrobersson.rshop.domain.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private ModelMapper mapper;

	@Transactional(readOnly = true)
	public List<Role> findAll(Sort sort) {
		List<Role> roles = repository.findAll(sort);
		repository.findRolesWithPrivileges(roles);
		return roles;
	}

	@Transactional(readOnly = true)
	public Role findById(Long roleId) {
		return findRoleOrThrow(roleId);
	}

	@Transactional
	public Role insert(RoleInputDTO roleInputDTO) {
		Role role = new Role();
		copyDtoToEntity(roleInputDTO, role);
		return repository.save(role);
	}

	@Transactional
	public Role update(Long roleId, RoleInputDTO roleInputDTO) {
		Role role = findRoleOrThrow(roleId);
		copyDtoToEntity(roleInputDTO, role);
		return repository.save(role);
	}

	public void delete(Long roleId) {
		try {
			repository.deleteById(roleId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + roleId + " not found ");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(RoleInputDTO roleInputDTO, Role role) {
		mapper.map(roleInputDTO, role);
		role.getPrivileges().clear();
		for (Long id : roleInputDTO.getPrivilegesIds()) {
			Privilege privilege = privilegeService.findById(id);
			role.getPrivileges().add(privilege);
		}
	}

	private Role findRoleOrThrow(Long roleId) {
		return repository.findByIdWithPrivileges(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role" + roleId + "not found"));
	}
}
