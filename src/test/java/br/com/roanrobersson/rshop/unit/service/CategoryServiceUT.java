package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.CategoryBuilder.DEPENDENT_ID;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.aNonExistingCategory;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.anExistingCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.builder.CategoryBuilder;
import br.com.roanrobersson.rshop.domain.dto.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@ExtendWith(SpringExtension.class)
class CategoryServiceUT {

	@InjectMocks
	private CategoryService service;

	@Mock
	private CategoryRepository repository;

	@Mock
	private CategoryMapper mapper;

	@Test
	void findAllPaged_ReturnCategoryPage() {
		Category category = anExistingCategory().build();
		PageImpl<Category> categories = new PageImpl<>(List.of(category));
		Pageable pageable = PageRequest.of(0, 10);
		when(repository.findAll(pageable)).thenReturn(categories);

		Page<Category> actual = service.list(pageable);
		
		assertThat(actual.getContent()).containsExactlyInAnyOrder(category)
				.usingRecursiveFieldByFieldElementComparator();
		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void findById_ReturnCategory_IdExist() {
		Category category = anExistingCategory().build();
		when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(category));

		Category actual = service.findById(EXISTING_ID);

		assertThat(actual).usingRecursiveComparison().isEqualTo(actual);
		verify(repository, times(1)).findById(EXISTING_ID);
	}

	@Test
	void findById_ThrowCategoryNotFoundException_IdDoesNotExist() {
		when(repository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.findById(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(CategoryNotFoundException.class);
		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}

	@Test
	void insert_ReturnInsertedCategory_InputValid() {
		CategoryBuilder builder = aNonExistingCategory();
		CategoryInput input = builder.buildInput();
		Category category = builder.build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toCategory(input)).thenReturn(category);
		when(repository.save(category)).thenReturn(category);

		Category actual = service.insert(input);

		assertThat(actual).usingRecursiveComparison().isEqualTo(actual);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toCategory(input);
		verify(repository, times(1)).save(category);
	}

	@Test
	void insert_ThrowsUniqueException_NameAlreadyInUse() {
		CategoryBuilder builder = aNonExistingCategory().withExistingName();
		CategoryInput input = builder.buildInput();
		Category category = builder.build();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(category));

		Throwable thrown = catchThrowable(() -> {
			service.insert(input);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void update_ReturnUpdatedCategory_IdExist() {
		CategoryBuilder builder = aNonExistingCategory();
		CategoryInput input = builder.buildInput();
		Category category = builder.withId(EXISTING_ID).build();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(category));
		when(repository.save(category)).thenReturn(category);

		Category actual = service.update(EXISTING_ID, input);

		assertThat(actual).isNotNull();
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

		Throwable thrown = catchThrowable(() -> {
			service.update(NON_EXISTING_ID, input);
		});

		assertThat(thrown).isExactlyInstanceOf(CategoryNotFoundException.class);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findById(NON_EXISTING_ID);
	}

	@Test
	void delete_DoNothing_IdExists() {
		doNothing().when(repository).deleteById(EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(EXISTING_ID);
		});

		assertThat(thrown).isNull();
		verify(repository, times(1)).deleteById(EXISTING_ID);
	}

	@Test
	void delete_ThrowCategoryNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(CategoryNotFoundException.class);
		verify(repository, times(1)).deleteById(NON_EXISTING_ID);
	}

	@Test
	void delete_ThrowEntityInUseException_DependentId() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(DEPENDENT_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(EntityInUseException.class);
		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
