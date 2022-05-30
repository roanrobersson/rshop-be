package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;

@Service
public class ProductService {

	private static final String MSG_PRODUCT_IN_USE = "Product with ID %s cannot be removed, because it is in use";
	private static final String MSG_PRODUCT_WITH_ONE_CATEGORY = "Category cannot be unlinked, because the product shoud have at least one category";
	private static final String MSG_PRODUCT_NAME_ALREADY_EXISTS = "There is already a product registered with that name %s";
	private static final String MSG_PRODUCT_SKU_ALREADY_EXISTS = "There is already a product registered with that SKU %s";

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductMapper mapper;

	@Transactional(readOnly = true)
	public Page<Product> list(Set<UUID> categoriesIds, String name, Pageable pageable) {
		if (categoriesIds.isEmpty()) {
			categoriesIds = null;
		}
		Page<Product> productPage = repository.search(categoriesIds, name, pageable);
		repository.findWithCategories(productPage.toList());
		return productPage;
	}

	@Transactional(readOnly = true)
	public Product findById(UUID productId) {
		return repository.findByIdWithCategories(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}

	@Transactional
	public Product insert(ProductInput productInput) {
		try {
			validateUniqueInsert(productInput);
			Product product = mapper.toProduct(productInput);
			repository.save(product);
			return product;
		} catch (CategoryNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional
	public Product update(UUID productId, ProductInput productInput) {
		try {
			validateUniqueUpdate(productId, productInput);
			Product product = findById(productId);
			mapper.update(productInput, product);
			return repository.save(product);
		} catch (CategoryNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public void delete(UUID productId) {
		try {
			repository.deleteById(productId);
		} catch (EmptyResultDataAccessException e) {
			throw new ProductNotFoundException(productId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_PRODUCT_IN_USE, productId));
		}
	}

	@Transactional(readOnly = true)
	public Long count() {
		return repository.count();
	}

	@Transactional(readOnly = true)
	public Set<Category> getCategories(UUID productId) {
		return findById(productId).getCategories();
	}

	@Transactional
	public void linkCategory(UUID productId, UUID categoryId) {
		Product product = findById(productId);
		Category category = categoryService.findById(categoryId);
		if (product.getCategories().contains(category)) {
			return;
		}
		product.getCategories().add(category);
		repository.save(product);
	}

	@Transactional
	public void unlinkCategory(UUID productId, UUID categoryId) {
		Product product = findById(productId);
		if (product.getCategories().size() <= 1) {
			throw new BusinessException(MSG_PRODUCT_WITH_ONE_CATEGORY);
		}
		Category category = categoryService.findById(categoryId);
		product.getCategories().remove(category);
		repository.save(product);
	}

	private void validateUniqueInsert(ProductInput productInput) {
		Optional<Product> optional = repository.findByName(productInput.getName());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_PRODUCT_NAME_ALREADY_EXISTS, productInput.getName()));
		}
		optional = repository.findBySku(productInput.getSku());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_PRODUCT_SKU_ALREADY_EXISTS, productInput.getSku()));
		}
	}

	private void validateUniqueUpdate(UUID productId, ProductInput productInput) {
		Optional<Product> optional = repository.findByName(productInput.getName());
		if (optional.isPresent() && !optional.get().getId().equals(productId)) {
			throw new UniqueException(String.format(MSG_PRODUCT_NAME_ALREADY_EXISTS, productInput.getName()));
		}
		optional = repository.findBySku(productInput.getSku());
		if (optional.isPresent() && !optional.get().getId().equals(productId)) {
			throw new UniqueException(String.format(MSG_PRODUCT_SKU_ALREADY_EXISTS, productInput.getSku()));
		}
	}
}
