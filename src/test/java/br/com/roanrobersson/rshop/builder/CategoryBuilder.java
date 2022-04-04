package br.com.roanrobersson.rshop.builder;

import java.time.Instant;
import java.util.UUID;

import br.com.roanrobersson.rshop.api.v1.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.api.v1.model.CategoryModel;
import br.com.roanrobersson.rshop.api.v1.model.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.model.Category;
import lombok.Getter;

@Getter
public class CategoryBuilder {

	public static final UUID EXISTING_ID = UUID.fromString("753dad79-2a1f-4f5c-bbd1-317a53587518");
	public static final UUID ANOTHER_EXISTING_ID = UUID.fromString("5c2b2b98-7b72-42dd-8add-9e97a2967e11");
	public static final UUID NON_EXISTING_ID = UUID.fromString("00000000-0000-4000-0000-000000000000");
	public static final UUID DEPENDENT_ID = UUID.fromString("821e3c67-7f22-46af-978c-b6269cb15387");
	public static final String NON_EXISTING_NAME = "Non existing category name";
	public static final String EXISTING_NAME = "Books";
	public static final String ANOTHER_EXISTING_NAME = "Computers";
	public static final Instant VALID_INSTANT = Instant.parse("2020-10-20T03:00:00Z");

	private UUID id = EXISTING_ID;
	private String name = EXISTING_NAME;

	public static CategoryBuilder aCategory() {
		return new CategoryBuilder();
	}

	public static CategoryBuilder anExistingCategory() {
		CategoryBuilder builder = new CategoryBuilder();
		builder.id = EXISTING_ID;
		builder.name = EXISTING_NAME;
		return builder;
	}

	public static CategoryBuilder aNonExistingCategory() {
		CategoryBuilder builder = new CategoryBuilder();
		builder.id = NON_EXISTING_ID;
		builder.name = NON_EXISTING_NAME;
		return builder;
	}

	public CategoryBuilder withId(UUID id) {
		this.id = id;
		return this;
	}

	public CategoryBuilder withExistingId() {
		this.id = EXISTING_ID;
		return this;
	}

	public CategoryBuilder withAnotherExistingId() {
		this.id = ANOTHER_EXISTING_ID;
		return this;
	}

	public CategoryBuilder withNonExistingId() {
		this.id = NON_EXISTING_ID;
		return this;
	}

	public CategoryBuilder withDependentId() {
		this.id = DEPENDENT_ID;
		return this;
	}

	public CategoryBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CategoryBuilder withNonExistingName() {
		this.name = NON_EXISTING_NAME;
		return this;
	}

	public CategoryBuilder withExistingName() {
		this.name = EXISTING_NAME;
		return this;
	}

	public CategoryBuilder withAnotherExistingName() {
		this.name = ANOTHER_EXISTING_NAME;
		return this;
	}

	public Category build() {
		return new Category(id, name, VALID_INSTANT, VALID_INSTANT);
	}

	public CategoryInput buildInput() {
		return CategoryMapper.INSTANCE.toCategoryInput(build());
	}

	public CategoryModel buildModel() {
		return CategoryMapper.INSTANCE.toCategoryModel(build());
	}
};