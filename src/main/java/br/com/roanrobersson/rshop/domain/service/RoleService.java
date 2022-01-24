package br.com.roanrobersson.rshop.domain.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.RoleDTO;
import br.com.roanrobersson.rshop.domain.Role;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private ModelMapper mapper;

	@Transactional(readOnly = true)
	public List<Role> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Transactional(readOnly = true)
	public Role findById(Long roleId) {
		return findRoleOrThrow(roleId);
	}

	@Transactional
	public Role insert(RoleDTO roleDTO) {
		Role role = mapper.map(roleDTO, Role.class);
		return repository.save(role);
	}

	@Transactional
	public Role update(Long roleId, RoleDTO roleDTO) {
		Role role = findRoleOrThrow(roleId);
		mapper.map(roleDTO, role);
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
		return repository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role" + roleId + "not found"));
	}
}
