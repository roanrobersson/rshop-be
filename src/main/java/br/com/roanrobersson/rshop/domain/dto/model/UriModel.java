package br.com.roanrobersson.rshop.domain.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aUriModel", toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Schema(title = "Uri")
public class UriModel {

	@EqualsAndHashCode.Include
	@Schema(example = "http://www.ficticiousimagehost.com/image.png")
	private String uri;

	public static UriModelBuilder anCategoryModel() {
		return new UriModelBuilder().uri("http://www.ficticiousimagehost.com/image.png");
	}
}
