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

import br.com.roanrobersson.rshop.api.v1.dto.AddressDTO;
import br.com.roanrobersson.rshop.domain.Address;
import br.com.roanrobersson.rshop.domain.User;
import br.com.roanrobersson.rshop.domain.repository.AddressRepository;
import br.com.roanrobersson.rshop.domain.service.exception.DatabaseException;
import br.com.roanrobersson.rshop.domain.service.exception.ForbiddenException;
import br.com.roanrobersson.rshop.domain.service.exception.ResourceNotFoundException;

@Service
public class AddressService {

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
		return findAddressOrThrow(userId, addressId);
	}

	@Transactional(readOnly = true)
	public Optional<Address> findMain(Long userId) {
		return repository.findFirstByUserIdAndMain(userId, true);
	}

	@Transactional
	public Address insert(Long userId, AddressDTO addressDTO) {
		Address address = mapper.map(addressDTO, Address.class);
		User user = userService.findById(userId);
		address.setUser(user);
		address.setMain(false);
		return repository.save(address);
	}

	@Transactional
	public Address update(Long userId, Long addressId, AddressDTO addressDTO) {
		Address address = findAddressOrThrow(userId, addressId);
		addressDTO.setMain(null);
		mapper.map(addressDTO, address);
		return repository.save(address);
	}

	public void delete(Long userId, Long addressId) {
		try {
			repository.deleteById(addressId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Shipping address " + addressId + " not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	@Transactional
	public void setMain(Long userId, Long addressId) {
		Address address = findAddressOrThrow(userId, addressId);
		if (address.getMain())
			return;
		unsetMain(userId);
		address.setMain(true);
		repository.save(address);
	}

	@Transactional
	public void unsetMain(Long userId) {
		Optional<Address> optional = findMain(userId);
		if (optional.isEmpty())
			return;
		Address address = optional.get();
		address.setMain(false);
		repository.save(address);
	}

	private Address findAddressOrThrow(Long userId, Long addressId) {
		Address address = repository.findByUserIdAndId(userId, addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address " + addressId + " not found"));
		validateAddressOwner(userId, address);
		return address;
	}

	private void validateAddressOwner(Long userId, Address address) {
		if (address.getUser().getId() != userId) {
			String msg = "Address " + address.getId() + " does not belong to user " + userId;
			throw new ForbiddenException(msg);
		}
	}
}
