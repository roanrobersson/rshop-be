package br.com.roanrobersson.rshop.dto.category;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.services.validation.category.CategoryInsertValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@CategoryInsertValid
@Getter @Setter @NoArgsConstructor
public class CategoryInsertDTO extends AbstractCategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public CategoryInsertDTO(Long id, String name) {
		super(id, name);
	}
	
	public CategoryInsertDTO(Category entity){
		super(entity);
	}
}
