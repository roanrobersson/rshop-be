package br.com.roanrobersson.rshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.dto.role.RoleInsertDTO;
import br.com.roanrobersson.rshop.dto.role.RoleResponseDTO;
import br.com.roanrobersson.rshop.dto.role.RoleUpdateDTO;
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
	public Page<RoleResponseDTO> findAllPaged(PageRequest pageRequest) {
		Page<Role> list = repository.findAll(pageRequest);
		return list.map(x -> new RoleResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public RoleResponseDTO findById(String id) { 
		Role entity = findOrThrow(id);
		return new RoleResponseDTO(entity);
	}

	@Transactional
	public RoleResponseDTO insert(RoleInsertDTO dto) {
		Role entity = modelMapper.map(dto, Role.class);
		entity = repository.save(entity);
		return new RoleResponseDTO(entity);
	}

	@Transactional
	public RoleResponseDTO update(String id, RoleUpdateDTO dto) {
		Role entity = findOrThrow(id);
		modelMapper.map(dto, entity);
		entity = repository.save(entity);
		return new RoleResponseDTO(entity);
	}
	
	public void delete(String id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not found ");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private Role findOrThrow(String roleId) {
		return repository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
}
