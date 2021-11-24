package br.com.roanrobersson.rshop.services.validation.address;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.roanrobersson.rshop.controllers.exceptions.FieldMessage;
import br.com.roanrobersson.rshop.dto.address.AddressInsertDTO;
import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.repositories.AddressRepository;

public class AddressInsertValidator implements ConstraintValidator<AddressInsertValid, AddressInsertDTO> {
	
	@Autowired
	private AddressRepository repository;
	
	@Override
	public void initialize(AddressInsertValid ann) {
	}

	@Override
	public boolean isValid(AddressInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Address address = repository.findByNick(dto.getNick());
		if (address != null) {
			list.add(new FieldMessage("nick", "Address is already in use"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}