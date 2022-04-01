package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.CategoryBuilder.DEPENDENT_ID;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.aNonExistingCategory;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.anExistingCategory;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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
import br.com.roanrobersson.rshop.builder.CategoryBuilder;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@ExtendWith(SpringExtension.class)
class CategoryServiceTests {

	@InjectMocks
	private CategoryService service;

	@Mock
	private CategoryRepository repository;

	@Mock
	private CategoryMapper mapper;

	@Test
	void findAllPaged_ReturnPage() {
		Category category = anExistingCategory().build();
		PageImpl<Category> categories = new PageImpl<>(List.of(category));
		PageRequest pageRequest = PageRequest.of(0, 10);
		when(repository.findAll(pageRequest)).thenReturn(categories);

		Page<Category> result = service.findAllPaged(pageRequest);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(result, categories);
		verify(repository, times(1)).findAll(pageRequest);
	}

	@Test
	void findById_ReturnCategoryModel_IdExist() {
		Category category = anExistingCategory().build();
		when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(category));

		Category result = service.findById(EXISTING_ID);

		assertNotNull(result);
		assertEquals(result, category);
		verify(repository, times(1)).findById(EXISTING_ID);
	}

	@Test
	void findById_ThrowCategoryNotFoundException_IdDoesNotExist() {
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

		assertThrows(CategoryNotFoundException.class, () -> {
			service.findById(NON_EXISTING_ID);
		});

		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}

	@Test
	void insert_ReturnCategoryModel_InputValid() {
		CategoryBuilder builder = aNonExistingCategory();
		CategoryInput input = builder.buildInput();
		Category category = builder.build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toCategory(input)).thenReturn(category);
		when(repository.save(category)).thenReturn(category);

		Category result = service.insert(input);

		assertNotNull(result);
		assertEquals(result, category);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toCategory(input);
		verify(repository, times(1)).save(category);
	}

	@Test
	void insert_ThrowsBusinessException_NameAlreadyInUse() {
		CategoryBuilder builder = aNonExistingCategory().withExistingName();
		CategoryInput input = builder.buildInput();
		Category category = builder.build();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(category));

		assertThrows(BusinessException.class, () -> {
			service.insert(input);
		});

		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void update_ReturnCategoryModel_IdExist() {
		CategoryBuilder builder = aNonExistingCategory();
		CategoryInput input = builder.buildInput();
		Category category = builder.withId(EXISTING_ID).build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(category));
		when(repository.save(category)).thenReturn(category);

		Category result = service.update(EXISTING_ID, input);

		assertNotNull(result);
		verify(repository, times(1)).findById(EXISTING_ID);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).update(input, category);
		verify(repository, times(1)).save(category);
	}

	@Test
	void update_ThrowCategoryNotFoundException_IdDoesNotExist() {
		CategoryInput input = aNonExistingCategory().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

		assertThrows(CategoryNotFoundException.class, () -> {
			service.update(NON_EXISTING_ID, input);
		});

		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}

	@Test
	void delete_DoNothing_IdExists() {
		doNothing().when(repository).deleteById(EXISTING_ID);

		assertDoesNotThrow(() -> {
			service.delete(EXISTING_ID);
		});

		verify(repository, times(1)).deleteById(EXISTING_ID);
	}

	@Test
	void delete_ThrowCategoryNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		assertThrows(CategoryNotFoundException.class, () -> {
			service.delete(NON_EXISTING_ID);
		});

		verify(repository, times(1)).deleteById(NON_EXISTING_ID);
	}

	@Test
	void delete_ThrowDatabaseException_DependentId() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);

		assertThrows(DatabaseException.class, () -> {
			service.delete(DEPENDENT_ID);
		});

		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
