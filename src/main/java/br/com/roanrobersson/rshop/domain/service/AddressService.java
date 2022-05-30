package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.api.v1.model.input.AddressInput;
import br.com.roanrobersson.rshop.domain.exception.AddressNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.AddressRepository;

@Service
public class AddressService {

	private static final String MSG_ADDRESS_IN_USE = "Address with ID %s cannot be removed, because it is in use";
	private static final String MSG_NO_MAIN_ADDRESS = "User with the ID %s dont have a main address";
	private static final String MSG_ADDRESS_ALREADY_EXISTS = "There is already a address registered with that nick %s";

	@Autowired
	private AddressRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;

	@Transactional(readOnly = true)
	public Page<Address> list(UUID userId, Pageable pageable) {
		return repository.findAllByUserId(userId, pageable);
	}

	@Transactional(readOnly = true)
	public Address findById(UUID userId, UUID addressId) {
		return repository.findByUserIdAndId(userId, addressId)
				.orElseThrow(() -> new AddressNotFoundException(addressId));
	}

	@Transactional(readOnly = true)
	public Address findMain(UUID userId) {
		Optional<Address> optional = repository.findFirstByUserIdAndMain(userId, true);
		return optional.orElseThrow(() -> new AddressNotFoundException(String.format(MSG_NO_MAIN_ADDRESS, userId)));
	}

	@Transactional
	public Address insert(UUID userId, AddressInput addressInput) {
		validateUniqueInsert(userId, addressInput);
		Address address = mapper.map(addressInput, Address.class);
		User user = userService.findById(userId);
		address.setUser(user);
		address.setMain(false);
		return repository.save(address);
	}

	@Transactional
	public Address update(UUID userId, UUID addressId, AddressInput addressInput) {
		validateUniqueUpdate(userId, addressId, addressInput);
		Address address = findById(userId, addressId);
		mapper.map(addressInput, address);
		return repository.save(address);
	}

	@Transactional
	public void delete(UUID userId, UUID addressId) {
		try {
			findById(userId, addressId);
			repository.deleteById(addressId);
		} catch (EmptyResultDataAccessException e) {
			throw new AddressNotFoundException(addressId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ADDRESS_IN_USE, addressId));
		}
	}

	@Transactional
	public void setMain(UUID userId, UUID addressId) {
		Address address = findById(userId, addressId);
		if (Boolean.TRUE.equals(address.getMain())) {
			return;
		}
		unsetMain(userId);
		address.setMain(true);
		repository.save(address);
	}

	@Transactional
	public void unsetMain(UUID userId) {
		Address address;
		address = findMain(userId);
		address.setMain(false);
		repository.save(address);
	}

	private void validateUniqueInsert(UUID userId, AddressInput addressInput) {
		Optional<Address> optional = repository.findByUserIdAndNick(userId, addressInput.getNick());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_ADDRESS_ALREADY_EXISTS, addressInput.getNick()));
		}
	}

	private void validateUniqueUpdate(UUID userId, UUID addressId, AddressInput addressInput) {
		Optional<Address> optional = repository.findByUserIdAndNick(userId, addressInput.getNick());
		if (optional.isPresent() && !optional.get().getId().equals(addressId)) {
			throw new UniqueException(String.format(MSG_ADDRESS_ALREADY_EXISTS, addressInput.getNick()));
		}
	}
}
