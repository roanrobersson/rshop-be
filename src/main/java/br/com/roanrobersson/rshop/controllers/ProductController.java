package br.com.roanrobersson.rshop.controllers;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roanrobersson.rshop.config.CheckSecurity;
import br.com.roanrobersson.rshop.domain.dto.ProductDTO;
import br.com.roanrobersson.rshop.domain.entities.Product;
import br.com.roanrobersson.rshop.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Product.CanConsult
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Product> products = service.findAllPaged(categoryId, name.trim(), pageRequest);
		Page<ProductDTO> productResponseDTOs = products.map(x -> mapper.map(x, ProductDTO.class));
		return ResponseEntity.ok().body(productResponseDTOs);
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ProductDTO> findById(@PathVariable Long productId) {
		Product product = service.findById(productId);
		ProductDTO productResponseDTO = mapper.map(product, ProductDTO.class);
		return ResponseEntity.ok().body(productResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@CheckSecurity.Product.CanEdit
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDTO) {
		Product product = service.insert(productDTO);
		ProductDTO productResponseDTO = convertToDto(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productResponseDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(productResponseDTO);
	}

	@PutMapping(value = "/{productId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Product.CanEdit
	public ResponseEntity<ProductDTO> update(@PathVariable Long productId, @Valid @RequestBody ProductDTO productDTO) {
		Product product = service.update(productId, productDTO);
		ProductDTO productResponseDTO = convertToDto(product);
		return ResponseEntity.ok().body(productResponseDTO);
	}

	@DeleteMapping(value = "/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Product.CanEdit
	public ResponseEntity<Void> delete(@PathVariable Long productId) {
		service.delete(productId);
		return ResponseEntity.noContent().build();
	}

	private ProductDTO convertToDto(Product product) {
		ProductDTO productDTO = mapper.map(product, ProductDTO.class);
		Set<Long> categoriesIds = product.getCategories().stream().map(x -> x.getId()).collect(Collectors.toSet());
		productDTO.setCategoriesIds(categoriesIds);
		return productDTO;
	}
}
