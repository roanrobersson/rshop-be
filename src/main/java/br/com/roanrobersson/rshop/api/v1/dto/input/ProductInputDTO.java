package br.com.roanrobersson.rshop.api.v1.dto.input;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
@ApiModel(value = "ProductInput")
@ToString(of = { "name", "categories" })
public class ProductInputDTO {

	@NotEmpty(message = "Must have a set of categories")
	@Builder.Default
	@ApiModelProperty(example = "[2, 4, 9]", required = true)
	private Set<Long> categories = new HashSet<>();

	@NotBlank(message = "Required field")
	@Size(min = 3, max = 127, message = "Must be between 8 and 127 characters")
	@ApiModelProperty(example = "Detergent", required = true)
	private String name;

	@NotBlank(message = "Required field")
	@Size(min = 5, max = 60, message = "Must be between 5 and 60 characters")
	@ApiModelProperty(example = "Great for washing dishes", required = true)
	private String description;

	@Positive(message = "Must be positive")
	@Digits(integer = 6, fraction = 2, message = "Must follow the format: 999999,99")
	@ApiModelProperty(example = "5.99", required = true)
	private BigDecimal price;

	@NotBlank(message = "Required field")
	@ApiModelProperty(example = "http://www.ficticiousimagehost.com/image.png", required = true)
	private String imgUrl;
}
