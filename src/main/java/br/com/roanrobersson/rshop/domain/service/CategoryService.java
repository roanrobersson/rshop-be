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
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;

@Service
public class CategoryService {

	private static final String MSG_CATEGORY_IN_USE = "Category with ID %d cannot be removed, because it is in use";

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
		return repository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
	}

	@Transactional
	public Category insert(CategoryInputDTO categoryInputDTO) {
		Category category = mapper.toCategory(categoryInputDTO);
		return repository.save(category);
	}

	@Transactional
	public Category update(Long categoryId, CategoryInputDTO categoryInputDTO) {
		Category category = findById(categoryId);
		mapper.update(categoryInputDTO, category);
		return repository.save(category);
	}

	public void delete(Long categoryId) {
		try {
			repository.deleteById(categoryId);
		} catch (EmptyResultDataAccessException e) {
			throw new CategoryNotFoundException(categoryId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_CATEGORY_IN_USE, categoryId));
		}
	}
}
