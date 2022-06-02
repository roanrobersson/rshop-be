package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.model.CountModel;
import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.ProductControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
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
	public ResponseEntity<Page<ProductModel>> list(
			@RequestParam(value = "categories", required = false) UUID[] categories,
			@RequestParam(value = "q", defaultValue = "") String name,
			@PageableDefault(size = 10) Pageable pageable) {
		if (categories == null)
			categories = new UUID[0];
		Page<Product> products = service.list(Set.of(categories), name.trim(), pageable);
		Page<ProductModel> productModels = products.map(x -> mapper.toProductModel(x));
		return ResponseEntity.ok().body(productModels);
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ProductModel> findById(@PathVariable UUID productId) {
		Product product = service.findById(productId);
		ProductModel productModel = mapper.toProductModel(product);
		return ResponseEntity.ok().body(productModel);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ProductModel> insert(@Valid @RequestBody ProductInput productInput) {
		Product product = service.insert(productInput);
		ProductModel productModel = mapper.toProductModel(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productModel.getId())
				.toUri();
		return ResponseEntity.created(uri).body(productModel);
	}

	@PutMapping(value = "/{productId}", produces = "application/json")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ProductModel> update(@PathVariable UUID productId,
			@Valid @RequestBody ProductInput productInput) {
		Product product = service.update(productId, productInput);
		ProductModel productModel = mapper.toProductModel(product);
		return ResponseEntity.ok().body(productModel);
	}

	@DeleteMapping(value = "/{productId}")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable UUID productId) {
		service.delete(productId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/count", produces = "application/json")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CountModel> count() {
		CountModel countModel = new CountModel(service.count());
		return ResponseEntity.ok().body(countModel);
	}
}
