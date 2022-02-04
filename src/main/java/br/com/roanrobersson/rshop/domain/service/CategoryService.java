package br.com.roanrobersson.rshop.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.CategoryInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private CategoryMapper mapper;

	@Transactional(readOnly = true)
	public Page<Category> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Transactional(readOnly = true)
	public Category findById(Long categoryId) {
		return findOrThrow(categoryId);
	}

	@Transactional
	public Category insert(CategoryInputDTO categoryInputDTO) {
		Category category = mapper.toCategory(categoryInputDTO);
		return repository.save(category);
	}

	@Transactional
	public Category update(Long categoryId, CategoryInputDTO categoryInputDTO) {
		Category category = findOrThrow(categoryId);
		mapper.update(categoryInputDTO, category);
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
