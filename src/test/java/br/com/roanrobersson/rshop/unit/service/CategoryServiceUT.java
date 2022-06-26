package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.domain.dto.input.CategoryInput.aCategoryInput;
import static br.com.roanrobersson.rshop.domain.model.Category.aCategory;
import static br.com.roanrobersson.rshop.util.ExceptionUtils.ignoreThrowsExactly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.STRICT_STUBS;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.domain.dto.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;
import br.com.roanrobersson.rshop.domain.service.CategoryService;

@ExtendWith(SpringExtension.class)
@MockitoSettings(strictness = STRICT_STUBS)
class CategoryServiceUT {

	private static final UUID CATEGORY_ID = UUID.fromString("11111111-1111-4111-1111-111111111111");
	private static final String CATEGORY_NAME = "Category name";
	private static final String NEW_CATEGORY_NAME = "New category name";

	@InjectMocks
	private CategoryService service;

	@Mock
	private CategoryRepository repository;

	@Mock
	private CategoryMapper mapper;

	private static Answer<Category> mapperToCategoryAnswer = invocation -> aCategory()
			.id(null)
			.name(((CategoryInput) invocation.getArgument(0)).getName())
			.build();

	private static Answer<Category> mapperUpdateAnswer = invocation -> {
		Category category = invocation.getArgument(1, Category.class);
		category.setName(((CategoryInput) invocation.getArgument(0)).getName());
		return category;
	};

	private static Function<UUID, Answer<Category>> repositorySaveInsertedCategoryAnswer = (UUID id) -> invocation -> {
		Category category = invocation.getArgument(0, Category.class);
		category.setId(id);
		return category;
	};

	private static Answer<Category> repositorySaveUpdatedCategoryAnswer = i -> i.getArgument(0);

	@Test
	void findAllPaged_ReturnCategoryPage() {
		final long TOTAL_PAGE_ITEMS = 50L;
		Category category = aCategory().build();
		Pageable pageable = PageRequest.of(0, 10);
		PageImpl<Category> categoryPage = new PageImpl<>(List.of(category), pageable, TOTAL_PAGE_ITEMS);
		Category expectedCategory = aCategory().build();
		PageImpl<Category> expected = new PageImpl<>(List.of(expectedCategory), pageable, TOTAL_PAGE_ITEMS);
		when(repository.findAll(pageable)).thenReturn(categoryPage);

		Page<Category> actual = service.list(pageable);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void findById_ReturnCorrectCategory_CategoryIdExist() {
		Category category = aCategory().build();
		Category expected = aCategory().build();
		when(repository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));

		Category actual = service.findById(CATEGORY_ID);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void findById_ThrowCategoryNotFoundException_CategoryIdDoesNotExist() {
		when(repository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.findById(CATEGORY_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(CategoryNotFoundException.class);
	}

	@Test
	void insert_ReturnInsertedCategoryWithGeneratedId_CategoryInputValid() {
		CategoryInput categoryInput = aCategoryInput().name(CATEGORY_NAME).build();
		Category expected = aCategory().id(CATEGORY_ID).name(CATEGORY_NAME).build();
		when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.empty());
		when(mapper.toCategory(categoryInput)).thenAnswer(mapperToCategoryAnswer);
		when(repository.save(argThat(c -> c.getId() == null)))
				.thenAnswer(repositorySaveInsertedCategoryAnswer.apply(CATEGORY_ID));

		Category actual = service.insert(categoryInput);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void insert_SaveInsertedCategory_CategoryInputValid() {
		CategoryInput categoryInput = aCategoryInput().name(CATEGORY_NAME).build();
		Category expected = aCategory().id(null).name(CATEGORY_NAME).build();
		when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.empty());
		when(mapper.toCategory(categoryInput)).thenAnswer(mapperToCategoryAnswer);

		service.insert(categoryInput);

		ArgumentCaptor<Category> categoryArgument = ArgumentCaptor.forClass(Category.class);
		verify(repository, times(1)).save(categoryArgument.capture());
		Category createdCategory = categoryArgument.getValue();
		assertThat(createdCategory).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void insert_VerifyCategoryNameUnique() throws Throwable {
		CategoryInput categoryInput = aCategoryInput().name(CATEGORY_NAME).build();

		service.insert(categoryInput);

		ArgumentCaptor<String> categoryArgument = ArgumentCaptor.forClass(String.class);
		verify(repository, times(1)).findByName(categoryArgument.capture());
		String categoryName = categoryArgument.getValue();
		assertThat(categoryName).isEqualTo(CATEGORY_NAME);
	}

	@Test
	void insert_ThrowsUniqueException_CategoryNameAlreadyInUse() {
		CategoryInput categoryInput = aCategoryInput().name(CATEGORY_NAME).build();
		Category category = aCategory().build();
		when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(category));

		Throwable thrown = catchThrowable(() -> {
			service.insert(categoryInput);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
	}

	@Test
	void insert_NotSaveCategoryInput_CategoryNameAlreadyInUse() throws Throwable {
		CategoryInput categoryInput = aCategoryInput().name(CATEGORY_NAME).build();
		Category category = aCategory().build();
		when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(category));

		ignoreThrowsExactly(UniqueException.class, () -> {
			service.insert(categoryInput);
		});

		verify(repository, never()).save(any());
	}

	@Test
	void update_ReturnUpdatedCategory_CategoryInputValidAndIdExist() {
		CategoryInput categoryInput = aCategoryInput().name(NEW_CATEGORY_NAME).build();
		Category category = aCategory().id(CATEGORY_ID).name(CATEGORY_NAME).build();
		Category expected = aCategory().id(CATEGORY_ID).name(NEW_CATEGORY_NAME).build();
		when(repository.findByName(NEW_CATEGORY_NAME)).thenReturn(Optional.empty());
		when(repository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
		doAnswer(mapperUpdateAnswer).when(mapper).update(any(CategoryInput.class), any(Category.class));
		when(repository.save(any(Category.class))).thenAnswer(repositorySaveUpdatedCategoryAnswer);

		Category actual = service.update(CATEGORY_ID, categoryInput);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void update_SaveUpdatedCategory_CategoryInputValidAndIdExist() {
		CategoryInput categoryInput = aCategoryInput().name(NEW_CATEGORY_NAME).build();
		Category category = aCategory().id(CATEGORY_ID).name(CATEGORY_NAME).build();
		Category expected = aCategory().id(CATEGORY_ID).name(NEW_CATEGORY_NAME).build();
		when(repository.findByName(NEW_CATEGORY_NAME)).thenReturn(Optional.empty());
		when(repository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
		doAnswer(mapperUpdateAnswer).when(mapper).update(any(CategoryInput.class), any(Category.class));
		when(repository.save(any(Category.class))).thenAnswer(repositorySaveUpdatedCategoryAnswer);

		service.update(CATEGORY_ID, categoryInput);

		ArgumentCaptor<Category> categoryArgument = ArgumentCaptor.forClass(Category.class);
		verify(repository, times(1)).save(categoryArgument.capture());
		Category createdCategory = categoryArgument.getValue();
		assertThat(createdCategory).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void update_VerifyCategoryNameUnique() throws Throwable {
		CategoryInput categoryInput = aCategoryInput().name(CATEGORY_NAME).build();
		Category category = aCategory().id(CATEGORY_ID).name(CATEGORY_NAME).build();
		when(repository.findByName(CATEGORY_NAME)).thenReturn(Optional.empty());
		when(repository.findById(any())).thenReturn(Optional.of(category));
		doAnswer(mapperUpdateAnswer).when(mapper).update(any(CategoryInput.class), any(Category.class));
		when(repository.save(any(Category.class))).thenAnswer(repositorySaveUpdatedCategoryAnswer);

		service.update(CATEGORY_ID, categoryInput);

		ArgumentCaptor<String> categoryArgument = ArgumentCaptor.forClass(String.class);
		verify(repository, times(1)).findByName(categoryArgument.capture());
		String categoryName = categoryArgument.getValue();
		assertThat(categoryName).isEqualTo(CATEGORY_NAME);
	}

	@Test
	void update_ThrowCategoryNotFoundException_CategoryIdDoesNotExist() {
		CategoryInput categoryInput = aCategoryInput().build();

		Throwable thrown = catchThrowable(() -> {
			service.update(CATEGORY_ID, categoryInput);
		});

		assertThat(thrown).isExactlyInstanceOf(CategoryNotFoundException.class);
	}

	@Test
	void update_NotSaveCategoryInput_CategoryNameAlreadyInUse() throws Throwable {
		final UUID ANOTHER_CATEGORY_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
		CategoryInput categoryInput = aCategoryInput().build();
		Category category = aCategory().id(ANOTHER_CATEGORY_ID).name(CATEGORY_NAME).build();
		when(repository.findByName(any())).thenReturn(Optional.of(category));

		ignoreThrowsExactly(UniqueException.class, () -> {
			service.update(CATEGORY_ID, categoryInput);
		});

		verify(repository, never()).save(any());
	}

	@Test
	void delete_DoNothing_CategoryIdExists() {

		Throwable thrown = catchThrowable(() -> {
			service.delete(CATEGORY_ID);
		});

		assertThat(thrown).isNull();
	}

	@Test
	void delete_DeleteTheCategory_CategoryIdExists() {

		service.delete(CATEGORY_ID);

		verify(repository, times(1)).deleteById(CATEGORY_ID);
	}

	@Test
	void delete_ThrowCategoryNotFoundException_CategoryIdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(CATEGORY_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(CATEGORY_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(CategoryNotFoundException.class);
	}

	@Test
	void delete_ThrowEntityInUseException_CategoryHasDependent() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(CATEGORY_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(CATEGORY_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(EntityInUseException.class);
	}

	@Test
	void count_ReturnCorrectCategoryCount() {
		final Long CATEGORY_COUNT = 555L;
		when(repository.count()).thenReturn(CATEGORY_COUNT);

		Long actual = service.count();

		assertThat(actual).isEqualTo(CATEGORY_COUNT);
	}
}
