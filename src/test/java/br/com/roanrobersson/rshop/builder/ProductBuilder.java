package br.com.roanrobersson.rshop.builder;

import static br.com.roanrobersson.rshop.builder.CategoryBuilder.aNonExistingCategory;
import static br.com.roanrobersson.rshop.builder.CategoryBuilder.anExistingCategory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.mapper.ProductMapper;
import br.com.roanrobersson.rshop.api.v1.model.ProductModel;
import br.com.roanrobersson.rshop.api.v1.model.input.ProductInput;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Product;
import lombok.Getter;

@Getter
public class ProductBuilder {

	public static final UUID EXISTING_ID = UUID.fromString("ff9d39d5-717f-4714-9688-9e75797c0ec0");
	public static final UUID ANOTHER_EXISTING_ID = UUID.fromString("f758d7cf-6005-4012-93fc-23afa45bf1ed");
	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-4000-0000-000000000000");
	public static final UUID DEPENDENT_ID = UUID.fromString("f758d7cf-6005-4012-93fc-23afa45bf1ed");
	public static final String EXISTING_NAME = "Smart TV";
	public static final String ANOTHER_EXISTING_NAME = "PC Gamer";
	public static final String NON_EXISTING_NAME = "Non existing name";
	public static final String EXISTING_SKU = "XBSO0200";
	public static final String ANOTHER_EXISTING_SKU = "PCPO01BL";
	public static final String NON_EXISTING_SKU = "NEN12345";
	public static final BigDecimal VALID_PRICE = BigDecimal.valueOf(800);
	public static final BigDecimal INVALID_PRICE = BigDecimal.valueOf(-1);
	public static final OffsetDateTime VALID_DATETIME = OffsetDateTime.parse("2020-10-20T03:00:00Z");
	public static final String VALID_URL = "https://img.com/img.png";
	public static final String VALID_DESCRIPTION = "Description";

	private UUID id = EXISTING_ID;
	private String name = EXISTING_NAME;
	private String sku = EXISTING_SKU;
	private Set<Category> categories = new HashSet<>();
	private BigDecimal price = VALID_PRICE;

	public static ProductBuilder aProduct() {
		return new ProductBuilder();
	}

	public static ProductBuilder anExistingProduct() {
		ProductBuilder builder = new ProductBuilder();
		builder.id = EXISTING_ID;
		builder.name = EXISTING_NAME;
		builder.sku = EXISTING_SKU;
		builder.categories.add(anExistingCategory().build());
		return builder;
	}

	public static ProductBuilder aNonExistingProduct() {
		ProductBuilder builder = new ProductBuilder();
		builder.id = NON_EXISTING_ID;
		builder.name = NON_EXISTING_NAME;
		builder.sku = NON_EXISTING_SKU;
		builder.categories.add(anExistingCategory().build());
		return builder;
	}

	public ProductBuilder withId(UUID id) {
		this.id = id;
		return this;
	}

	public ProductBuilder withExistingId() {
		this.id = EXISTING_ID;
		return this;
	}

	public ProductBuilder withAnotherExistingId() {
		this.id = ANOTHER_EXISTING_ID;
		return this;
	}

	public ProductBuilder withNonExistingId() {
		this.id = NON_EXISTING_ID;
		return this;
	}

	public ProductBuilder withDependentId() {
		this.id = DEPENDENT_ID;
		return this;
	}

	public ProductBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ProductBuilder withExistingName() {
		this.name = EXISTING_NAME;
		return this;
	}

	public ProductBuilder withAnotherExistingName() {
		this.name = ANOTHER_EXISTING_NAME;
		return this;
	}

	public ProductBuilder withNonExistingName() {
		this.name = NON_EXISTING_NAME;
		return this;
	}

	public ProductBuilder withSKU(String sku) {
		this.sku = sku;
		return this;
	}

	public ProductBuilder withExistingSKU() {
		this.sku = EXISTING_SKU;
		return this;
	}

	public ProductBuilder withAnotherExistingSKU() {
		this.sku = ANOTHER_EXISTING_SKU;
		return this;
	}

	public ProductBuilder withNonExistingSKU() {
		this.sku = NON_EXISTING_SKU;
		return this;
	}

	public ProductBuilder withCategory(Category category) {
		categories.add(category);
		return this;
	}

	public ProductBuilder withExistingCategory() {
		Category category = anExistingCategory().build();
		categories.add(category);
		return this;
	}

	public ProductBuilder withNonExistingCategory() {
		Category category = aNonExistingCategory().build();
		categories.add(category);
		return this;
	}

	public ProductBuilder withoutCategories() {
		categories.clear();
		return this;
	}

	public ProductBuilder withPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public ProductBuilder withValidPrice() {
		this.price = VALID_PRICE;
		return this;
	}

	public ProductBuilder withInvalidPrice() {
		this.price = INVALID_PRICE;
		return this;
	}

	public Product build() {
		return new Product(id, categories, sku, name, VALID_DESCRIPTION, price, VALID_URL, VALID_DATETIME,
				VALID_DATETIME);
	}

	public ProductInput buildInput() {
		return ProductMapper.INSTANCE.toInput(build());
	}

	public ProductModel buildModel() {
		return ProductMapper.INSTANCE.toModel(build());
	}
}