package br.com.roanrobersson.rshop.dto.category;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.services.validation.category.CategoryUpdateValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@CategoryUpdateValid
@Getter @Setter @NoArgsConstructor
public class CategoryUpdateDTO extends AbstractCategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public CategoryUpdateDTO(Long id, String name) {
		super(id, name);
	}
	
	public CategoryUpdateDTO(Category entity){
		super(entity);
	}
}
