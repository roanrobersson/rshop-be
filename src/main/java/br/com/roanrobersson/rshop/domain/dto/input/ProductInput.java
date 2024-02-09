package br.com.roanrobersson.rshop.domain.dto.input;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.roanrobersson.rshop.domain.dto.input.id.CategoryIdInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aProductInput", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = { "name", "categories" })
@Schema(title = "ProductInput")
public class ProductInput {

	@Valid
	@NotEmpty
	@Setter(value = AccessLevel.NONE)
	@Schema(example = "[\"123\", \"456\"]")
	@Singular(ignoreNullCollections = true)
	private Set<CategoryIdInput> categories = new HashSet<>();

	@NotBlank
	@Size(min = 8, max = 12)
	@EqualsAndHashCode.Include
	@Schema(example = "KS944RUR")
	private String sku;

	@NotBlank
	@Size(min = 3, max = 127)
	@EqualsAndHashCode.Include
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

	public static ProductInputBuilder aProductInput() {
		return new ProductInputBuilder()
				.name("Keyboard")
				.description("A black keyboard for gaming")
				.price(BigDecimal.valueOf(50.00))
				.imgUrl("http://www.ficticiousimagehost.com/image.png");
	}
}
