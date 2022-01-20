package br.com.roanrobersson.rshop.services;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.dto.ProductDTO;
import br.com.roanrobersson.rshop.domain.entities.Category;
import br.com.roanrobersson.rshop.domain.entities.Product;
import br.com.roanrobersson.rshop.repositories.ProductRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModelMapper mapper;

	@Transactional(readOnly = true)
	public Page<Product> findAllPaged(Long categoryId, String name, PageRequest pageRequest) {
		List<Category> categoryList = (categoryId == 0) ? null : Arrays.asList(categoryService.findById(categoryId));
		Page<Product> productPage = repository.search(categoryList, name, pageRequest);
		repository.findProductsWithCategories(productPage.toList());
		return productPage;
	}

	@Transactional(readOnly = true)
	public Product findById(Long productId) {
		return findOrThrow(productId);
	}

	@Transactional
	public Product insert(ProductDTO productDTO) {
		Product product = new Product();
		copyDtoToEntity(productDTO, product);
		return repository.save(product);
	}

	@Transactional
	public Product update(Long productId, ProductDTO productDTO) {
		Product product = findOrThrow(productId);
		copyDtoToEntity(productDTO, product);
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

	private void copyDtoToEntity(ProductDTO productDTO, Product product) {
		mapper.map(productDTO, product);
		product.getCategories().clear();
		for (Long id : productDTO.getCategoriesIds()) {
			Category category = categoryService.findById(id);
			product.getCategories().add(category);
		}
	}

	private Product findOrThrow(Long productId) {
		return repository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found"));
	}
}
