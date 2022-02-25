package br.com.roanrobersson.rshop.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;
import br.com.roanrobersson.rshop.domain.service.CategoryService;
import br.com.roanrobersson.rshop.factory.CategoryFactory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@InjectMocks
	private CategoryService service;

	@Mock
	private CategoryRepository repository;

	@Mock
	private CategoryMapper mapper;

	private UUID existingId;
	private UUID nonExistingId;
	private UUID dependentId;
	private Category category;
	private PageImpl<Category> categories;
	private CategoryInput categoryInput;

	@BeforeEach
	void setUp() throws Exception {
		existingId = UUID.fromString("753dad79-2a1f-4f5c-bbd1-317a53587518");
		nonExistingId = UUID.fromString("00000000-0000-0000-0000-000000000000");
		dependentId = UUID.fromString("5c2b2b98-7b72-42dd-8add-9e97a2967e11");
		categoryInput = CategoryFactory.createCategoryInput();
		category = CategoryFactory.createCategory();
		categories = new PageImpl<>(List.of(category));

		// findAllPaged
		when(repository.findAll(any(PageRequest.class))).thenReturn(categories);

		// findById
		when(repository.findById(existingId)).thenReturn(Optional.of(category));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// insert
		when(repository.save(any())).thenReturn(category);

		// update
		when(repository.getById(existingId)).thenReturn(category);
		doThrow(CategoryNotFoundException.class).when(repository).getById(nonExistingId);

		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}

	@Test
	public void findAllPaged_ReturnPage() {
		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<Category> result = service.findAllPaged(pageRequest);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).findAll(pageRequest);
	}

	@Test
	public void findById_ReturnCategoryModel_IdExist() {

		Category result = service.findById(existingId);

		assertNotNull(result);
	}

	@Test
	public void findById_ThrowCategoryNotFoundException_IdDoesNotExist() {

		assertThrows(CategoryNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void insert_ReturnCategoryModel() {

		Category result = service.insert(categoryInput);

		assertNotNull(result);
	}

	@Test
	public void update_ReturnCategoryModel_IdExist() {

		Category result = service.update(existingId, categoryInput);

		assertNotNull(result);
	}

	@Test
	public void update_ThrowCategoryNotFoundException_IdDoesNotExist() {

		assertThrows(CategoryNotFoundException.class, () -> {
			service.update(nonExistingId, categoryInput);
		});
	}

	@Test
	public void delete_DoNothingIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void delete_ThrowCategoryNotFoundException_IdDoesNotExist() {

		assertThrows(CategoryNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		verify(repository, times(1)).deleteById(nonExistingId);
	}

	@Test
	public void delete_ThrowDatabaseException_DependentId() {

		assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		verify(repository, times(1)).deleteById(dependentId);
	}
}
