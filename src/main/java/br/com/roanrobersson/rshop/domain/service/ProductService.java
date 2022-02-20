package br.com.roanrobersson.rshop.domain.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;

@Service
public class ProductService {

	private static final String MSG_PRODUCT_IN_USE = "Product with ID %s cannot be removed, because it is in use";
	private static final String MSG_PRODUCT_WITHOUT_CATEGORY = "Category cannot be unlinked, because the product shoud have at least one category";

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductMapper mapper;

	@Transactional(readOnly = true)
	public Page<Product> findAllPaged(Set<UUID> categoriesIds, String name, PageRequest pageRequest) {
		if (categoriesIds.size() == 0)
			categoriesIds = null;
		Page<Product> productPage = repository.search(categoriesIds, name, pageRequest);
		repository.findWithCategories(productPage.toList());
		return productPage;
	}

	@Transactional(readOnly = true)
	public Product findById(UUID productId) {
		return repository.findByIdWithCategories(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}

	@Transactional
	public Product insert(ProductInputDTO productInputDTO) {
		Product product = mapper.toProduct(productInputDTO);
		return repository.save(product);
	}

	@Transactional
	public Product update(UUID productId, ProductInputDTO productInputDTO) {
		Product product = findById(productId);
		mapper.update(productInputDTO, product);
		return repository.save(product);
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
	public Set<Category> getCategories(UUID productId) {
		return findById(productId).getCategories();
	}

	@Transactional
	public void linkCategory(UUID productId, UUID categoryId) {
		Product product = findById(productId);
		Category category = categoryService.findById(categoryId);
		product.getCategories().add(category);
		repository.save(product);
	}

	@Transactional
	public void unlinkCategory(UUID productId, UUID categoryId) {
		Product product = findById(productId);
		if (product.getCategories().size() == 1) {
			throw new BusinessException(MSG_PRODUCT_WITHOUT_CATEGORY);
		}
		Category category = categoryService.findById(categoryId);
		product.getCategories().remove(category);
		repository.save(product);
	}
}
