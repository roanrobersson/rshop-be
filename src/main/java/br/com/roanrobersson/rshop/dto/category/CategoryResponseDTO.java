package br.com.roanrobersson.rshop.dto.category;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CategoryResponseDTO extends AbstractCategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public CategoryResponseDTO(Long id, String name) {
		super(id, name);
	}
	
	public CategoryResponseDTO(Category entity){
		super(entity);
	}
}
