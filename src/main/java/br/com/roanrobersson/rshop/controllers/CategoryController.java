package br.com.roanrobersson.rshop.controllers;

import java.net.URI;

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
import br.com.roanrobersson.rshop.domain.dto.CategoryDTO;
import br.com.roanrobersson.rshop.domain.entities.Category;
import br.com.roanrobersson.rshop.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Category.CanConsult
	public ResponseEntity<Page<CategoryDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Category> categories = service.findAllPaged(pageRequest);
		Page<CategoryDTO> categoryResponseDTOs = categories.map(x -> mapper.map(x, CategoryDTO.class));
		return ResponseEntity.ok().body(categoryResponseDTOs);
	}

	@GetMapping(value = "/{categoryId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Category.CanConsult
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long categoryId) {
		Category category = service.findById(categoryId);
		CategoryDTO categoryResponseDTO = mapper.map(category, CategoryDTO.class);
		return ResponseEntity.ok().body(categoryResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@CheckSecurity.Category.CanEdit
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = service.insert(categoryDTO);
		CategoryDTO categoryResponseDTO = mapper.map(category, CategoryDTO.class);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoryResponseDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(categoryResponseDTO);
	}

	@PutMapping(value = "/{categoryId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@CheckSecurity.Category.CanEdit
	public ResponseEntity<CategoryDTO> update(@PathVariable Long categoryId,
			@Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = service.update(categoryId, categoryDTO);
		CategoryDTO categoryResponseDTO = mapper.map(category, CategoryDTO.class);
		return ResponseEntity.ok().body(categoryResponseDTO);
	}

	@DeleteMapping(value = "/{categoryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckSecurity.Category.CanEdit
	public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
		service.delete(categoryId);
		return ResponseEntity.noContent().build();
	}

}
