package br.com.roanrobersson.rshop.api.v1.model;

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
@Schema(title = "Category")
@ToString
public class CategoryModel {

	@Schema(example = "821e3c677f2246af978cb6269cb15387")
	private UUID id;

	@Schema(example = "Cleaning")
	private String name;
}
