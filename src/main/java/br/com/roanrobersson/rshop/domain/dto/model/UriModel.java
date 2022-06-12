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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(title = "Uri")
@ToString
public class UriModel {

	@EqualsAndHashCode.Include
	@Schema(example = "http://www.ficticiousimagehost.com/image.png")
	private String uri;
}
