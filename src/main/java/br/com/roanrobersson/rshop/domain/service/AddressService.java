package br.com.roanrobersson.rshop.domain.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.dto.input.AddressInputDTO;
import br.com.roanrobersson.rshop.domain.exception.AddressNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.EntityNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.ForbiddenException;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.AddressRepository;

@Service
public class AddressService {

	private static final String MSG_ADDRESS_IN_USE = "Address with ID %d cannot be removed, because it is in use";

	@Autowired
	private AddressRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;

	@Transactional(readOnly = true)
	public List<Address> findAll(Long userId, Sort sort) {
		return repository.findAllByUserId(userId, sort);
	}

	@Transactional(readOnly = true)
	public Address findById(Long userId, Long addressId) {
		Address address = repository.findByUserIdAndId(userId, addressId)
				.orElseThrow(() -> new AddressNotFoundException(addressId));
		validateAddressOwner(userId, address);
		return address;
	}

	@Transactional(readOnly = true)
	public Address findMain(Long userId) {
		Optional<Address> optional = repository.findFirstByUserIdAndMain(userId, true);
		return optional.orElseThrow(() -> new AddressNotFoundException(
				String.format("User with the ID %d dont have a main address", userId)));
	}

	@Transactional
	public Address insert(Long userId, AddressInputDTO addressInputDTO) {
		Address address = mapper.map(addressInputDTO, Address.class);
		User user = userService.findById(userId);
		address.setUser(user);
		address.setMain(false);
		return repository.save(address);
	}

	@Transactional
	public Address update(Long userId, Long addressId, AddressInputDTO addressInputDTO) {
		Address address = findById(userId, addressId);
		mapper.map(addressInputDTO, address);
		return repository.save(address);
	}

	public void delete(Long userId, Long addressId) {
		try {
			repository.deleteById(addressId);
		} catch (EmptyResultDataAccessException e) {
			throw new AddressNotFoundException(addressId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ADDRESS_IN_USE, addressId));
		}
	}

	@Transactional
	public void setMain(Long userId, Long addressId) {
		Address address = findById(userId, addressId);
		if (address.getMain())
			return;
		unsetMain(userId);
		address.setMain(true);
		repository.save(address);
	}

	@Transactional
	public void unsetMain(Long userId) {
		Address address;
		try {
			address = findMain(userId);
		} catch (EntityNotFoundException e) {
			return;
		}
		address.setMain(false);
		repository.save(address);
	}

	private void validateAddressOwner(Long userId, Address address) {
		if (address.getUser().getId() != userId) {
			String message = String.format("Address with ID %d does not belong to user", userId);
			throw new ForbiddenException(message);
		}
	}
}
