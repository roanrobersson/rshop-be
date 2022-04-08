package br.com.roanrobersson.rshop.api.v1.model.input;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import br.com.roanrobersson.rshop.core.validation.FileContentType;
import br.com.roanrobersson.rshop.core.validation.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Schema(title = "ImageFileInput")
public class ImageFileInput {

	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	@Schema(example = "vaca")
	private MultipartFile file;
}
