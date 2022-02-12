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

import br.com.roanrobersson.rshop.api.v1.dto.CategoryDTO;
import br.com.roanrobersson.rshop.api.v1.dto.input.CategoryInputDTO;
import br.com.roanrobersson.rshop.core.security.CheckSecurity;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/categories")
@Api(tags = "Category")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = "application/json")
	@CheckSecurity.Category.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrive a categories page")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<Page<CategoryDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Category> categories = service.findAllPaged(pageRequest);
		Page<CategoryDTO> categoryResponseDTOs = categories.map(x -> mapper.map(x, CategoryDTO.class));
		return ResponseEntity.ok().body(categoryResponseDTOs);
	}

	@GetMapping(value = "/{categoryId}", produces = "application/json")
	@CheckSecurity.Category.CanConsult
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrives a category by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrived with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<CategoryDTO> findById(@PathVariable UUID categoryId) {
		Category category = service.findById(categoryId);
		CategoryDTO categoryResponseDTO = mapper.map(category, CategoryDTO.class);
		return ResponseEntity.ok().body(categoryResponseDTO);
	}

	@PostMapping(produces = "application/json")
	@CheckSecurity.Category.CanEdit
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a new category")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryInputDTO categoryInputDTO) {
		Category category = service.insert(categoryInputDTO);
		CategoryDTO categoryResponseDTO = mapper.map(category, CategoryDTO.class);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoryResponseDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(categoryResponseDTO);
	}

	@PutMapping(value = "/{categoryId}", produces = "application/json")
	@CheckSecurity.Category.CanEdit
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updates an existing category")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Updated with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 422, message = "Unprocessable entity"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<CategoryDTO> update(@PathVariable UUID categoryId,
			@Valid @RequestBody CategoryInputDTO categoryInputDTO) {
		Category category = service.update(categoryId, categoryInputDTO);
		CategoryDTO categoryResponseDTO = mapper.map(category, CategoryDTO.class);
		return ResponseEntity.ok().body(categoryResponseDTO);
	}

	@DeleteMapping(value = "/{categoryId}")
	@CheckSecurity.Category.CanEdit
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Remove an existing category")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removed with success"),
			@ApiResponse(code = 401, message = "Unauthorized access"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<Void> delete(@PathVariable UUID categoryId) {
		service.delete(categoryId);
		return ResponseEntity.noContent().build();
	}
}
