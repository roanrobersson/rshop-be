package br.com.roanrobersson.rshop.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.RoleInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.RoleMapper;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private RoleMapper mapper;

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
		Role role = mapper.toRole(roleInputDTO);
		role = repository.save(role);
		System.out.println(role.toString());
		return role;
	}

	@Transactional
	public Role update(Long roleId, RoleInputDTO roleInputDTO) {
		Role role = findRoleOrThrow(roleId);
		mapper.update(roleInputDTO, role);
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

	private Role findRoleOrThrow(Long roleId) {
		return repository.findByIdWithPrivileges(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role " + roleId + " not found"));
	}
}
