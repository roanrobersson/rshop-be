package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.DEPENDENT_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.anExistingProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.builder.CategoryBuilder;
import br.com.roanrobersson.rshop.builder.ProductBuilder;
import br.com.roanrobersson.rshop.domain.dto.input.ProductInput;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.mapper.ProductMapper;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.CategoryService;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@ExtendWith(SpringExtension.class)
class ProductServiceUT {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	@Mock
	private CategoryService categoryService;

	@Mock
	ProductMapper mapper;

	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by(Order.asc("id")));

	@Test
	void findAllPaged_ReturnProductPage() {
		Product product = anExistingProduct().build();
		List<Product> productList = List.of(product);
		PageImpl<Product> products = new PageImpl<>(productList);
		Set<UUID> categories = Set.of(CategoryBuilder.EXISTING_ID, CategoryBuilder.ANOTHER_EXISTING_ID);
		when(repository.search(categories, "", DEFAULT_PAGEABLE)).thenReturn(products);

		Page<Product> actual = service.list(categories, "", DEFAULT_PAGEABLE);

		assertThat(actual.getContent()).containsExactlyInAnyOrder(product)
				.usingRecursiveFieldByFieldElementComparator();
		verify(repository, times(1)).search(categories, "", DEFAULT_PAGEABLE);
		verify(repository, times(1)).findWithCategories(products.toList());
	}

	@Test
	void findById_ReturnProduct_IdExist() {
		Product product = anExistingProduct().build();
		UUID id = product.getId();
		when(repository.findByIdWithCategories(id)).thenReturn(Optional.of(product));

		Product actual = service.findById(id);

		assertThat(actual).usingRecursiveComparison().isEqualTo(product);
		verify(repository, times(1)).findByIdWithCategories(id);
	}

	@Test
	void findById_ThrowProductNotFoundException_IdDoesNotExist() {

		Throwable thrown = catchThrowable(() -> {
			service.findById(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(ProductNotFoundException.class);
		verify(repository, times(1)).findByIdWithCategories(NON_EXISTING_ID);
	}

	@Test
	void insert_ReturnProduct_InputValid() {
		ProductBuilder builder = aNonExistingProduct();
		Product product = builder.build();
		ProductInput input = builder.buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toProduct(input)).thenReturn(product);
		when(repository.save(product)).thenReturn(product);

		Product actual = service.insert(input);

		assertThat(actual).usingRecursiveComparison().isEqualTo(actual);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toProduct(input);
		verify(repository, times(1)).save(product);
	}

	@Test
	void insert_ThrowsUniqueException_NameAlreadyInUse() {
		ProductInput input = aNonExistingProduct().withExistingName().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(aProduct().build()));

		Throwable thrown = catchThrowable(() -> {
			service.insert(input);
		});

		assertThat(thrown).isExactlyInstanceOf(UniqueException.class);
		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	void insert_ThrowsBusinessException_CategoryNonExist() {
		ProductInput input = aProduct().withNonExistingCategory().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toProduct(input)).thenThrow(CategoryNotFoundException.class);

		Throwable thrown = catchThrowable(() -> {
			service.insert(input);
		});

		assertThat(thrown).isExactlyInstanceOf(BusinessException.class);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toProduct(input);
	}

	@Test
	void update_ReturnProduct_IdExist() {
		ProductBuilder builder = aNonExistingProduct().withId(EXISTING_ID);
		Product product = builder.build();
		ProductInput input = builder.withId(EXISTING_ID).buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithCategories(EXISTING_ID)).thenReturn(Optional.of(product));
		when(repository.save(product)).thenReturn(product);

		Product actual = service.update(EXISTING_ID, input);

		assertThat(actual).usingRecursiveComparison().isEqualTo(actual);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithCategories(EXISTING_ID);
		verify(mapper, times(1)).update(input, product);
		verify(repository, times(1)).save(product);
	}

	@Test
	void update_ThrowProductNotFoundException_IdDoesNotExist() {
		ProductInput input = aNonExistingProduct().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithCategories(NON_EXISTING_ID)).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> {
			service.update(NON_EXISTING_ID, input);
		});

		assertThat(thrown).isExactlyInstanceOf(ProductNotFoundException.class);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithCategories(NON_EXISTING_ID);
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
	void delete_ThrowProductNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		Throwable thrown = catchThrowable(() -> {
			service.delete(NON_EXISTING_ID);
		});

		assertThat(thrown).isExactlyInstanceOf(ProductNotFoundException.class);
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
