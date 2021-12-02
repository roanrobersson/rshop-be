package br.com.roanrobersson.rshop.dto.response;

import java.time.Instant;

import br.com.roanrobersson.rshop.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

	private Long id;
	private String name;
	private Instant createdAt;
	private Instant updatedAt;

	public CategoryResponseDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.createdAt = category.getCreatedAt();
		this.updatedAt = category.getUpdatedAt();
	}
}
