package br.com.roanrobersson.rshop.dto.category;

import java.io.Serializable;

import br.com.roanrobersson.rshop.entities.Category;

public class CategoryResponseDTO extends AbstractCategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	public CategoryResponseDTO(){
		super();
	}
	
	public CategoryResponseDTO(Long id, String name) {
		super(id, name);
	}
	
	public CategoryResponseDTO(Category entity){
		super(entity);
	}
}
