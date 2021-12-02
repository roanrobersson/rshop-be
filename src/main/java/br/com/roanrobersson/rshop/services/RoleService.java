package br.com.roanrobersson.rshop.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.dto.RoleDTO;
import br.com.roanrobersson.rshop.entities.Role;
import br.com.roanrobersson.rshop.repositories.RoleRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private ModelMapper modelMapper;

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
		Role role = modelMapper.map(roleDTO, Role.class);
		return repository.save(role);
	}

	@Transactional
	public Role update(Long roleId, RoleDTO roleDTO) {
		Role role = findRoleOrThrow(roleId);
		modelMapper.map(roleDTO, role);
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
