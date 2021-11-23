package br.com.roanrobersson.rshop.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.roanrobersson.rshop.dto.category.CategoryInsertDTO;
import br.com.roanrobersson.rshop.dto.category.CategoryResponseDTO;
import br.com.roanrobersson.rshop.dto.category.CategoryUpdateDTO;
import br.com.roanrobersson.rshop.services.CategoryService;


@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping(produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<CategoryResponseDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CategoryResponseDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
		CategoryResponseDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
	public ResponseEntity<CategoryResponseDTO> insert(@Valid @RequestBody CategoryInsertDTO dto) {
		CategoryResponseDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
	public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO dto) {
		CategoryResponseDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
