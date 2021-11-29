package br.com.roanrobersson.rshop.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductResponseDTO(entity);
	}

	@Transactional
	public ProductResponseDTO insert(ProductInsertDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductResponseDTO(entity);
	}
	
	@Transactional
	public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
		try {
			Product entity = repository.getById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductResponseDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found ");
		}
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
	
	private void copyDtoToEntity(ProductInsertDTO dto, Product entity) {
		modelMapper.map(dto, entity);
		entity.getCategories().clear();
		for (CategoryResponseDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getById(catDto.getId());
			entity.getCategories().add(category);
		}
	}
	
	private void copyDtoToEntity(ProductUpdateDTO dto, Product entity) {
		modelMapper.map(dto, entity);		
		entity.getCategories().clear();
		for (CategoryResponseDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getById(catDto.getId());
			entity.getCategories().add(category);
		}
	}
}
