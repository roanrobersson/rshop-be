package br.com.roanrobersson.rshop.api.v1.controller;

import java.net.URI;
import java.util.UUID;

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

import br.com.roanrobersson.rshop.api.v1.model.CategoryModel;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import br.com.roanrobersson.rshop.api.v1.openapi.controller.CategoryControllerOpenApi;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@RestController
@RequestMapping(value = "/v1/categories")
public class CategoryController implements CategoryControllerOpenApi {

	@Autowired
	private CategoryService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Category.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<CategoryModel>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Category> categories = service.findAllPaged(pageRequest);
		Page<CategoryModel> categoryModels = categories.map(x -> mapper.map(x, CategoryModel.class));
		return ResponseEntity.ok().body(categoryModels);
	}

	@GetMapping(value = "/{categoryId}", produces = "application/json")
	@CheckSecurity.Category.CanConsult
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CategoryModel> findById(@PathVariable("categoryId") UUID categoryId) {
		Category category = service.findById(categoryId);
		CategoryModel categoryModel = mapper.map(category, CategoryModel.class);
		return ResponseEntity.ok().body(categoryModel);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Category.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CategoryModel> insert(@Valid @RequestBody CategoryInput categoryInput) {
		Category category = service.insert(categoryInput);
		CategoryModel categoryModel = mapper.map(category, CategoryModel.class);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryModel.getId())
				.toUri();
		return ResponseEntity.created(uri).body(categoryModel);
	}

	@PutMapping(value = "/{categoryId}", produces = "application/json")
	@CheckSecurity.Category.CanEdit
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CategoryModel> update(@PathVariable UUID categoryId,
			@Valid @RequestBody CategoryInput categoryInput) {
		Category category = service.update(categoryId, categoryInput);
		CategoryModel categoryModel = mapper.map(category, CategoryModel.class);
		return ResponseEntity.ok().body(categoryModel);
	}

	@DeleteMapping(value = "/{categoryId}")
	@CheckSecurity.Category.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable UUID categoryId) {
		service.delete(categoryId);
		return ResponseEntity.noContent().build();
	}
}
