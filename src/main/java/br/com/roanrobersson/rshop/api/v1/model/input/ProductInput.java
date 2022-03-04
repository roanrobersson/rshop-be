package br.com.roanrobersson.rshop.api.v1.model.input;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.api.v1.model.input.id.CategoryIdInput;
import br.com.roanrobersson.rshop.domain.validation.ProductInputValid;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "ProductInput")
@ToString(of = { "name", "categories" })
public class ProductInput {

	@Valid
	@NotEmpty
	@Builder.Default
	@Schema(example = "[\"753dad79-2a1f-4f5c-bbd1-317a53587518\", \"5227c10f-c81a-4885-b460-dbfee6dcc019\"]")
	private Set<CategoryIdInput> categories = new HashSet<>();

	@NotBlank
	@Size(min = 8, max = 12)
	@Schema(example = "KS944RUR")
	private String sku;

	@NotBlank
	@Size(min = 3, max = 127)
	@Schema(example = "Detergent", required = true)
	private String name;

	@NotBlank
	@Size(min = 5, max = 60)
	@Schema(example = "Great for washing dishes", required = true)
	private String description;

	@Positive
	@Digits(integer = 6, fraction = 2)
	@Schema(example = "5.99", required = true)
	private BigDecimal price;

	@NotBlank
	@Schema(example = "http://www.ficticiousimagehost.com/image.png", required = true)
	private String imgUrl;
}
