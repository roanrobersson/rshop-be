package br.com.roanrobersson.rshop.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.roanrobersson.rshop.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

	private Long id;
	private List<Long> categories = new ArrayList<>();
	private String name;
	private String description;
	private BigDecimal price;
	private String imgUrl;
	private Instant createdAt;
	private Instant updatedAt;

	public ProductResponseDTO(Product product) {
		this.id = product.getId();
		this.categories = product.getCategories().stream().map(c -> c.getId()).collect(Collectors.toList());
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.createdAt = product.getCreatedAt();
		this.updatedAt = product.getUpdatedAt();
	}
}
