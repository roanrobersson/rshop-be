package br.com.roanrobersson.rshop.api.exception;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(title = "Problem")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@Schema(example = "400")
	private Integer status;

	@Schema(example = "2019-12-01T18:09:02.70844Z")
	private OffsetDateTime timestamp;

	@Schema(example = "https://rshop.com.br/invalid-data")
	private String type;

	@Schema(example = "Dados inválidos")
	private String title;

	@Schema(example = "One or more fields are invalid. Please fill in correctly and try again.")
	private String detail;

	@Schema(example = "One or more fields are invalid. Please fill in correctly and try again.")
	private String userMessage;

	@Schema(example = "List of objects or fields that generated the error (optional)")
	private List<Object> objects;

	@Schema(title = "ProblemObject")
	@Getter
	@Builder
	public static class Object {

		@Schema(example = "price")
		private String name;

		@Schema(example = "The price is required")
		private String userMessage;
	}
}
