package br.com.roanrobersson.rshop.api.v1.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.com.roanrobersson.rshop.core.validation.ProductInputValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ProductInputValid
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Product")
@ToString(of = { "id", "name", "categories" })
public class ProductDTO {

	@ApiModelProperty(example = "2")
	private Long id;

	@Builder.Default
	@ApiModelProperty(example = "[2, 4, 9]")
	private Set<Long> categories = new HashSet<>();

	@ApiModelProperty(example = "Detergent")
	private String name;

	@ApiModelProperty(example = "Great for washing dishes")
	private String description;

	@ApiModelProperty(example = "5.99")
	private BigDecimal price;

	@ApiModelProperty(example = "http://www.ficticiousimagehost.com/image.png")
	private String imgUrl;
}
