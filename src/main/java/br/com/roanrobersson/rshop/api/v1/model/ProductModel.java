package br.com.roanrobersson.rshop.api.v1.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
public class ProductModel {

	@ApiModelProperty(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@ApiModelProperty(example = "[2, 4, 9]")
	private Set<UUID> categories = new HashSet<>();

	@ApiModelProperty(example = "KS944RUR")
	private String sku;

	@ApiModelProperty(example = "Detergent")
	private String name;

	@ApiModelProperty(example = "Great for washing dishes")
	private String description;

	@ApiModelProperty(example = "5.99")
	private BigDecimal price;

	@ApiModelProperty(example = "http://www.ficticiousimagehost.com/image.png")
	private String imgUrl;
}
