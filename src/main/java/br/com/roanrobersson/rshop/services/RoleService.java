package br.com.roanrobersson.rshop.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
	
	@Transactional(readOnly = true)
	public Page<RoleResponseDTO> findAllPaged(PageRequest pageRequest) {
		Page<Role> list = repository.findAll(pageRequest);
		return list.map(x -> new RoleResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public RoleResponseDTO findById(String id) {
		Optional<Role> obj = repository.findById(id);
		Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new RoleResponseDTO(entity);
	}

	@Transactional
	public RoleResponseDTO insert(RoleInsertDTO dto) {
		Role entity = new Role();
		entity.setAuthority(dto.getAuthority());
		entity = repository.save(entity);
		return new RoleResponseDTO(entity);
	}

	@Transactional
	public RoleResponseDTO update(String id, RoleUpdateDTO dto) {
		try {
			Role entity = repository.getById(id);
			entity.setAuthority(dto.getAuthority());
			entity = repository.save(entity);
			return new RoleResponseDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found ");
		}
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
}
