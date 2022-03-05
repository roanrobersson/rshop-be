package br.com.roanrobersson.rshop.api.v1.model;

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
@Schema(title = "Uri")
@ToString
public class UriModel {

	@Schema(example = "http://www.ficticiousimagehost.com/image.png")
	private String uri;
}
