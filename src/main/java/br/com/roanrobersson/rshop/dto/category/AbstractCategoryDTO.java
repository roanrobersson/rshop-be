package br.com.roanrobersson.rshop.dto.category;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractCategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 3, max = 127, message = "Deve ter entre 8 e 127 caracteres")
	private String name;
	
	public AbstractCategoryDTO(Category entity) {
		this.name = entity.getName();
	}
}
