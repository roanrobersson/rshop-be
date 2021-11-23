package br.com.roanrobersson.rshop.dto.product;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.dto.category.CategoryResponseDTO;
import br.com.roanrobersson.rshop.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo requerido")
	@Size(min = 3, max = 127, message = "Deve ter entre 8 e 127 caracteres")
	private String name;
	
	@NotBlank(message = "Campo requerido")
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	private String description;
	
	@Positive(message = "Preço deve ser positivo")
	private Double price;
	
	@NotBlank(message = "Campo requerido")
	private String imgUrl;
	
	@PastOrPresent(message = "A data do produto não pode ser futura")
	private Instant date;
	
	@NotEmpty(message = "Produto sem categoria não é permitido")
	private List<CategoryResponseDTO> categories = new ArrayList<>();

	public AbstractProductDTO(String name, String description, Double price, String imgUrl, Instant date) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public AbstractProductDTO(Product entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
		this.categories = entity.getCategories().stream().map((x) -> new CategoryResponseDTO(x)).collect(Collectors.toList());
	}
}
