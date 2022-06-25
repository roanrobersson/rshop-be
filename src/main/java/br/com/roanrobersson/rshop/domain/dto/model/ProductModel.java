package br.com.roanrobersson.rshop.domain.dto.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
@Builder(builderMethodName = "aProductModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(title = "Product")
@ToString(of = { "id", "name", "categories" })
public class ProductModel {

	@EqualsAndHashCode.Include
	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;
	
	@Setter(value = AccessLevel.NONE)
	@Singular
	private Set<CategoryModel> categories = new HashSet<>();

	@Schema(example = "KS944RUR")
	private String sku;

	@Schema(example = "Detergent")
	private String name;

	@Schema(example = "Great for washing dishes")
	private String description;

	@Schema(example = "5.99")
	private BigDecimal price;

	@Schema(example = "http://www.ficticiousimagehost.com/image.png")
	private String imgUrl;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime createdAt;

	@Schema(example = "1656178570.000000000")
	private OffsetDateTime updatedAt;

	public static ProductModelBuilder aProductModel() {
		UUID uuid = UUID.fromString("00000000-0000-4000-0000-000000000000");
		OffsetDateTime offsetDateTime = OffsetDateTime.parse("2020-10-20T03:00:00Z");
		return new ProductModelBuilder()
				.id(uuid)
				.name("Keyboard")
				.description("A black keyboard for gaming")
				.price(BigDecimal.valueOf(50.00))
				.imgUrl("http://www.ficticiousimagehost.com/image.png")
				.createdAt(offsetDateTime)
				.updatedAt(offsetDateTime);
	}
}
