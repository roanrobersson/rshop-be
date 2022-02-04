package br.com.roanrobersson.rshop.domain.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ResourceNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductMapper mapper;

	@Transactional(readOnly = true)
	public Page<Product> findAllPaged(Set<Long> categoriesIds, String name, PageRequest pageRequest) {
		if (categoriesIds.size() == 0)
			categoriesIds = null;
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
		Product product = mapper.toProduct(productInputDTO);
		return repository.save(product);
	}

	@Transactional
	public Product update(Long productId, ProductInputDTO productInputDTO) {
		Product product = findOrThrow(productId);
		mapper.update(productInputDTO, product);
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

	private Product findOrThrow(Long productId) {
		return repository.findByIdWithCategories(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found"));
	}
}
