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

import br.com.roanrobersson.rshop.dto.category.CategoryResponseDTO;
import br.com.roanrobersson.rshop.dto.product.ProductInsertDTO;
import br.com.roanrobersson.rshop.dto.product.ProductResponseDTO;
import br.com.roanrobersson.rshop.dto.product.ProductUpdateDTO;
import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.entities.Product;
import br.com.roanrobersson.rshop.repositories.CategoryRepository;
import br.com.roanrobersson.rshop.repositories.ProductRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public Page<ProductResponseDTO> findAllPaged(Long categoryId, String name, PageRequest pageRequest) {
		List<Category> categories = (categoryId == 0) ? null : Arrays.asList(categoryRepository.getById(categoryId));
		Page<Product> page = repository.search(categories, name, pageRequest);
		repository.findProductsWithCategories(page.toList());
		return page.map(x -> new ProductResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductResponseDTO findById(Long id) {
		Product entity = findOrThrow(id);
		return new ProductResponseDTO(entity);
	}

	@Transactional
	public ProductResponseDTO insert(ProductInsertDTO productInsertDTO) {
		Product entity = new Product();
		copyDtoToEntity(productInsertDTO, entity);
		entity = repository.save(entity);
		return new ProductResponseDTO(entity);
	}
	
	@Transactional
	public ProductResponseDTO update(Long id, ProductUpdateDTO productUpdateDTO) {
		Product entity = findOrThrow(id);
		copyDtoToEntity(productUpdateDTO, entity);
		entity = repository.save(entity);
		return new ProductResponseDTO(entity);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not found ");
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProductInsertDTO productInsertDTO, Product entity) {
		modelMapper.map(productInsertDTO, entity);
		entity.getCategories().clear();
		for (CategoryResponseDTO categoryResponseDTO : productInsertDTO.getCategories()) {
			Category category = categoryRepository.getById(categoryResponseDTO.getId());
			entity.getCategories().add(category);
		}
	}
	
	private void copyDtoToEntity(ProductUpdateDTO productUpdateDTO, Product entity) {
		modelMapper.map(productUpdateDTO, entity);		
		entity.getCategories().clear();
		for (CategoryResponseDTO categoryResponseDTO : productUpdateDTO.getCategories()) {
			Category category = categoryRepository.getById(categoryResponseDTO.getId());
			entity.getCategories().add(category);
		}
	}
	
	private Product findOrThrow(Long productId) {
		return repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}
}
