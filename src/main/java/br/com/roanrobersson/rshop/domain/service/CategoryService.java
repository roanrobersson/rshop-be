package br.com.roanrobersson.rshop.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.CategoryDTO;
import br.com.roanrobersson.rshop.domain.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<Category> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Transactional(readOnly = true)
	public Category findById(Long categoryId) {
		return findOrThrow(categoryId);
	}

	@Transactional
	public Category insert(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		return repository.save(category);
	}

	@Transactional
	public Category update(Long categoryId, CategoryDTO categoryDTO) {
		Category category = findOrThrow(categoryId);
		category.setName(categoryDTO.getName());
		return repository.save(category);
	}

	public void delete(Long categoryId) {
		try {
			repository.deleteById(categoryId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + categoryId + " not found ");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private Category findOrThrow(Long categoryId) {
		return repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}
}
