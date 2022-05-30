package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;

@Service
public class CategoryService {

	private static final String MSG_CATEGORY_IN_USE = "Category with ID %s cannot be removed, because it is in use";
	private static final String MSG_CATEGORY_ALREADY_EXISTS = "There is already a category registered with that name %s";

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CategoryMapper mapper;

	@Transactional(readOnly = true)
	public Page<Category> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Category findById(UUID categoryId) {
		return repository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
	}

	@Transactional
	public Category insert(CategoryInput categoryInput) {
		validateUniqueInsert(categoryInput);
		Category category = mapper.toCategory(categoryInput);
		return repository.save(category);
	}

	@Transactional
	public Category update(UUID categoryId, CategoryInput categoryInput) {
		validateUniqueUpdate(categoryId, categoryInput);
		Category category = findById(categoryId);
		mapper.update(categoryInput, category);
		return repository.save(category);
	}

	public void delete(UUID categoryId) {
		try {
			repository.deleteById(categoryId);
		} catch (EmptyResultDataAccessException e) {
			throw new CategoryNotFoundException(categoryId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_CATEGORY_IN_USE, categoryId));
		}
	}
	
	private void validateUniqueInsert(CategoryInput categoryInput) {
		Optional<Category> optional = repository.findByName(categoryInput.getName());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_CATEGORY_ALREADY_EXISTS, categoryInput.getName()));
		}
	}
	
	private void validateUniqueUpdate(UUID categoryId, CategoryInput categoryInput) {
		Optional<Category> optional = repository.findByName(categoryInput.getName());
		if (optional.isPresent() && !optional.get().getId().equals(categoryId)) {
			throw new UniqueException(String.format(MSG_CATEGORY_ALREADY_EXISTS, categoryInput.getName()));
		}
	}
}
