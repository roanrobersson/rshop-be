package br.com.roanrobersson.rshop.unit.service;

import static br.com.roanrobersson.rshop.builder.ProductBuilder.DEPENDENT_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.NON_EXISTING_ID;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aNonExistingProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.aProduct;
import static br.com.roanrobersson.rshop.builder.ProductBuilder.anExistingProduct;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.builder.CategoryBuilder;
import br.com.roanrobersson.rshop.builder.ProductBuilder;
import br.com.roanrobersson.rshop.domain.exception.BusinessException;
import br.com.roanrobersson.rshop.domain.exception.CategoryNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.CategoryService;
import br.com.roanrobersson.rshop.domain.service.ProductService;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	@Mock
	private CategoryService categoryService;

	@Mock
	ProductMapper mapper;

	@Test
	public void findAllPaged_ReturnPage() {
		Product product = anExistingProduct().build();
		PageImpl<Product> products = new PageImpl<>(List.of(product));
		Set<UUID> categories = Set.of(CategoryBuilder.EXISTING_ID, CategoryBuilder.ANOTHER_EXISTING_ID);
		PageRequest pageRequest = PageRequest.of(0, 10);
		when(repository.search(categories, "", pageRequest)).thenReturn(products);

		Page<Product> result = service.findAllPaged(categories, "", pageRequest);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(result, products);
		verify(repository, times(1)).search(categories, "", pageRequest);
		verify(repository, times(1)).findWithCategories(products.toList());
	}

	@Test
	public void findById_ReturnProductModel_IdExist() {
		Product product = anExistingProduct().build();
		UUID id = product.getId();
		when(repository.findByIdWithCategories(id)).thenReturn(Optional.of(product));

		Product result = service.findById(id);

		assertNotNull(result);
		assertEquals(result, product);
		verify(repository, times(1)).findByIdWithCategories(id);
	}

	@Test
	public void findById_ThrowProductNotFoundException_IdDoesNotExist() {
		when(repository.findByIdWithCategories(NON_EXISTING_ID)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> {
			service.findById(NON_EXISTING_ID);
		});

		verify(repository, times(1)).findByIdWithCategories(NON_EXISTING_ID);
	}

	@Test
	public void insert_ReturnProductModel_InputValid() {
		ProductBuilder builder = aNonExistingProduct();
		Product product = builder.build();
		ProductInput input = builder.buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toProduct(input)).thenReturn(product);
		when(repository.save(product)).thenReturn(product);

		Product result = service.insert(input);

		assertNotNull(result);
		assertEquals(result, product);
		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toProduct(input);
		verify(repository, times(1)).save(product);
	}

	@Test
	public void insert_ThrowsBusinessException_NameAlreadyInUse() {
		ProductInput input = aNonExistingProduct().withExistingName().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.of(aProduct().build()));

		assertThrows(BusinessException.class, () -> {
			service.insert(input);
		});

		verify(repository, times(1)).findByName(input.getName());
	}

	@Test
	public void insert_ThrowsBusinessException_CategoryNonExist() {
		ProductInput input = aProduct().withNonExistingCategory().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(mapper.toProduct(input)).thenThrow(CategoryNotFoundException.class);

		assertThrows(BusinessException.class, () -> {
			service.insert(input);
		});

		verify(repository, times(1)).findByName(input.getName());
		verify(mapper, times(1)).toProduct(input);
	}

	@Test
	public void update_ReturnProductModel_IdExist() {
		ProductBuilder builder = aNonExistingProduct().withId(EXISTING_ID);
		Product product = builder.build();
		ProductInput input = builder.withId(EXISTING_ID).buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithCategories(EXISTING_ID)).thenReturn(Optional.of(product));
		when(repository.save(product)).thenReturn(product);

		Product result = service.update(EXISTING_ID, input);

		assertNotNull(result);
		assertEquals(result, product);
		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithCategories(EXISTING_ID);
		verify(mapper, times(1)).update(input, product);
		verify(repository, times(1)).save(product);
	}

	@Test
	public void update_ThrowBusinessException_IdDoesNotExist() {
		ProductInput input = aNonExistingProduct().buildInput();
		when(repository.findByName(input.getName())).thenReturn(Optional.empty());
		when(repository.findByIdWithCategories(NON_EXISTING_ID)).thenReturn(Optional.empty());

		assertThrows(BusinessException.class, () -> {
			service.update(NON_EXISTING_ID, input);
		});

		verify(repository, times(1)).findByName(input.getName());
		verify(repository, times(1)).findByIdWithCategories(NON_EXISTING_ID);
	}

	@Test
	public void delete_DoNothing_IdExists() {
		doNothing().when(repository).deleteById(EXISTING_ID);

		assertDoesNotThrow(() -> {
			service.delete(EXISTING_ID);
		});

		verify(repository, times(1)).deleteById(EXISTING_ID);
	}

	@Test
	public void delete_ThrowProductNotFoundException_IdDoesNotExist() {
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NON_EXISTING_ID);

		assertThrows(ProductNotFoundException.class, () -> {
			service.delete(NON_EXISTING_ID);
		});

		verify(repository, times(1)).deleteById(NON_EXISTING_ID);
	}

	@Test
	public void delete_ThrowDatabaseException_DependentId() {
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(DEPENDENT_ID);

		assertThrows(DatabaseException.class, () -> {
			service.delete(DEPENDENT_ID);
		});

		verify(repository, times(1)).deleteById(DEPENDENT_ID);
	}
}
