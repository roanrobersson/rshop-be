package br.com.roanrobersson.rshop.builder;

import java.time.OffsetDateTime;

import br.com.roanrobersson.rshop.domain.dto.input.CategoryInput;
import br.com.roanrobersson.rshop.domain.dto.model.CategoryModel;
import br.com.roanrobersson.rshop.domain.mapper.CategoryMapper;
import br.com.roanrobersson.rshop.domain.model.Category;
import lombok.Getter;

@Getter
public class CategoryBuilder {

	public static final Long EXISTING_ID = 1L;
	public static final Long ANOTHER_EXISTING_ID = 2L;
	public static final Long NON_EXISTING_ID = 999999L;
	public static final Long DEPENDENT_ID = 3L;
	public static final String NON_EXISTING_NAME = "Non existing category name";
	public static final String EXISTING_NAME = "Books";
	public static final String ANOTHER_EXISTING_NAME = "Computers";
	public static final OffsetDateTime VALID_DATETIME = OffsetDateTime.parse("2020-10-20T03:00:00Z");

	private Long id = EXISTING_ID;
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

	public CategoryBuilder withId(Long id) {
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
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		category.setCreatedAt(VALID_DATETIME);
		category.setUpdatedAt(VALID_DATETIME);
		return category;
	}

	public CategoryInput buildInput() {
		return CategoryMapper.INSTANCE.toInput(build());
	}

	public CategoryModel buildModel() {
		return CategoryMapper.INSTANCE.toModel(build());
	}
};