package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

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

import br.com.roanrobersson.rshop.api.v1.dto.ProductDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.ProductControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductController implements ProductControllerOpenApi {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Product.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "categories", required = false) UUID[] categories,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		if (categories == null)
			categories = new UUID[0];
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Product> products = service.findAllPaged(Set.of(categories), name.trim(), pageRequest);
		Page<ProductDTO> productResponseDTOs = products.map(x -> mapper.toProductDTO(x));
		return ResponseEntity.ok().body(productResponseDTOs);
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ProductDTO> findById(@PathVariable UUID productId) {
		Product product = service.findById(productId);
		ProductDTO productResponseDTO = mapper.toProductDTO(product);
		return ResponseEntity.ok().body(productResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductInputDTO productInputDTO) {
		try {
			Product product = service.insert(productInputDTO);
			ProductDTO productResponseDTO = mapper.toProductDTO(product);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(productResponseDTO.getId()).toUri();
			return ResponseEntity.created(uri).body(productResponseDTO);
		} catch (CategoryNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping(value = "/{productId}", produces = "application/json")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ProductDTO> update(@PathVariable UUID productId,
			@Valid @RequestBody ProductInputDTO productInputDTO) {
		try {
			Product product = service.update(productId, productInputDTO);
			ProductDTO productResponseDTO = mapper.toProductDTO(product);
			return ResponseEntity.ok().body(productResponseDTO);
		} catch (CategoryNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/{productId}")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable UUID productId) {
		service.delete(productId);
		return ResponseEntity.noContent().build();
	}
}
