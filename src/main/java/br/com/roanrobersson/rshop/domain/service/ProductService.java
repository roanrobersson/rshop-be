package br.com.roanrobersson.rshop.domain.service;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.domain.Category;
import br.com.roanrobersson.rshop.domain.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelMapper mapper;

	@Transactional(readOnly = true)
	public Page<Product> findAllPaged(Set<Long> categoriesIds, String name, PageRequest pageRequest) {
		if (categoriesIds.size() == 0) categoriesIds = null;
		Page<Product> productPage = repository.search(categoriesIds, name, pageRequest);
		repository.findWithCategories(productPage.toList());
		return productPage;
	}

	@Transactional(readOnly = true)
	public Product findById(Long productId) {
		return findOrThrow(productId);
	}

	@Transactional
	public Product insert(ProductInputDTO productInputDTO) {
		Product product = new Product();
		copyDtoToEntity(productInputDTO, product);
		return repository.save(product);
	}

	@Transactional
	public Product update(Long productId, ProductInputDTO productInputDTO) {
		Product product = findOrThrow(productId);
		copyDtoToEntity(productInputDTO, product);
		return repository.save(product);
	}

	public void delete(Long productId) {
		try {
			repository.deleteById(productId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + productId + " not found ");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(ProductInputDTO productInputDTO, Product product) {
		mapper.map(productInputDTO, product);
		product.getCategories().clear();
		for (Long id : productInputDTO.getCategoriesIds()) {
			Category category = categoryService.findById(id);
			product.getCategories().add(category);
		}
	}

	private Product findOrThrow(Long productId) {
		return repository.findByIdWithCategories(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found"));
	}
}
