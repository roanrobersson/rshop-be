package br.com.roanrobersson.rshop.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import br.com.roanrobersson.rshop.api.v1.dto.input.ProductInputDTO;
import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.domain.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.exception.ProductNotFoundException;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.service.ProductService;
import br.com.roanrobersson.rshop.factory.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	@Mock
	private ProductMapper mapper;

	private UUID existingId;
	private UUID nonExistingId;
	private UUID dependentId;
	private Product product;
	private PageImpl<Product> products;
	private ProductInputDTO productInputDTO;

	@BeforeEach
	void setUp() throws Exception {
		existingId = UUID.fromString("7c4125cc-8116-4f11-8fc3-f40a0775aec7");
		nonExistingId = UUID.fromString("00000000-0000-0000-0000-000000000000");
		dependentId = UUID.fromString("f758d7cf-6005-4012-93fc-23afa45bf1ed");
		product = ProductFactory.createProduct();
		products = new PageImpl<>(List.of(product));
		productInputDTO = new ProductInputDTO();

		// findAllPaged
		when(repository.search(anySet(), anyString(), any(PageRequest.class))).thenReturn(products);

		// findById
		when(repository.findByIdWithCategories(existingId)).thenReturn(Optional.of(product));
		when(repository.findByIdWithCategories(nonExistingId)).thenReturn(Optional.empty());

		// insert
		when(repository.save(any())).thenReturn(product);

		// update
		when(repository.getById(existingId)).thenReturn(product);
		doThrow(ProductNotFoundException.class).when(repository).getById(nonExistingId);

		// delete
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		Set<UUID> categories = Set.of(UUID.fromString("753dad79-2a1f-4f5c-bbd1-317a53587518"),
				UUID.fromString("431d856e-caf2-4367-823a-924ce46b2e02"),
				UUID.fromString("5c2b2b98-7b72-42dd-8add-9e97a2967e11"));
		String name = "";
		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<Product> result = service.findAllPaged(categories, name, pageRequest);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(repository, times(1)).search(categories, name, pageRequest);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExist() {

		Product result = service.findById(existingId);

		assertNotNull(result);
	}

	@Test
	public void findByIdShouldShouldThrowProductNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ProductNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}

	@Test
	public void insertShouldReturnProductDTO() {

		Product result = service.insert(productInputDTO);

		assertNotNull(result);
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExist() {

		Product result = service.update(existingId, productInputDTO);

		assertNotNull(result);
	}

	@Test
	public void updateShouldThrowProductNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ProductNotFoundException.class, () -> {
			service.update(nonExistingId, productInputDTO);
		});
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowProductNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ProductNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		verify(repository, times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {

		assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		verify(repository, times(1)).deleteById(dependentId);
	}
}
