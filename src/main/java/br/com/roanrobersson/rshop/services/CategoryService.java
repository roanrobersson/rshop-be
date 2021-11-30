package br.com.roanrobersson.rshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.dto.category.CategoryInsertDTO;
import br.com.roanrobersson.rshop.dto.category.CategoryResponseDTO;
import br.com.roanrobersson.rshop.dto.category.CategoryUpdateDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryResponseDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryResponseDTO findById(Long id) {
		Category entity = findOrThrow(id);
		return new CategoryResponseDTO(entity);
	}

	@Transactional
	public CategoryResponseDTO insert(CategoryInsertDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryResponseDTO(entity);
	}

	@Transactional
	public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto) {
		Category entity = findOrThrow(id);
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryResponseDTO(entity);
	}
	
	public void delete(Long id) {
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
	
	private Category findOrThrow(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
}
