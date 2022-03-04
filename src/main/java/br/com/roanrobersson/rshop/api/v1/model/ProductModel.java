package br.com.roanrobersson.rshop.api.v1.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Product")
@ToString(of = { "id", "name", "categories" })
public class ProductModel {

	@Schema(example = "821e3c67-7f22-46af-978c-b6269cb15387")
	private UUID id;

	@Builder.Default
	@Schema(example = "[5c2b2b98-7b72-42dd-8add-9e97a2967e11, 431d856e-caf2-4367-823a-924ce46b2e02]")
	private Set<UUID> categories = new HashSet<>();

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
}
