package br.com.roanrobersson.rshop.api.v1.controller;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roanrobersson.rshop.api.v1.openapi.controller.ProductCategoryControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@RestController
@RequestMapping(value = "/v1/products/{productId}/categories")
public class ProductCategoryController implements ProductCategoryControllerOpenApi {

	@Autowired
	ProductService service;

	@GetMapping
	@CheckSecurity.Product.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Set<UUID>> findAll(@PathVariable UUID productId) {
		Set<UUID> categoriesIds = service.getCategories(productId).stream().map(Category::getId)
				.collect(Collectors.toSet());
		return ResponseEntity.ok().body(categoriesIds);
	}

	@PutMapping(value = "/{categoryId}")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> link(@PathVariable UUID productId, @PathVariable UUID categoryId) {
		service.linkCategory(productId, categoryId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{categoryId}")
	@CheckSecurity.Product.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> unlink(@PathVariable UUID productId, @PathVariable UUID categoryId) {
		service.unlinkCategory(productId, categoryId);
		return ResponseEntity.noContent().build();
	}
}
