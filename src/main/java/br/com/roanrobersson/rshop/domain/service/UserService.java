package br.com.roanrobersson.rshop.domain.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.domain.dto.input.AddressInput;
import br.com.roanrobersson.rshop.domain.dto.input.UserChangePasswordInput;
import br.com.roanrobersson.rshop.domain.dto.input.UserInsert;
import br.com.roanrobersson.rshop.domain.dto.input.UserUpdate;
import br.com.roanrobersson.rshop.domain.event.OnRegistrationCompleteEvent;
import br.com.roanrobersson.rshop.domain.exception.AddressNotFoundException;
import br.com.roanrobersson.rshop.domain.exception.EntityInUseException;
import br.com.roanrobersson.rshop.domain.exception.UniqueException;
import br.com.roanrobersson.rshop.domain.exception.UserNotFoundException;
import br.com.roanrobersson.rshop.domain.mapper.AddressMapper;
import br.com.roanrobersson.rshop.domain.mapper.UserMapper;
import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.AddressRepository;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Service
public class UserService {

	private static final String MSG_USER_IN_USE = "User with ID %s cannot be removed, because it is in use";
	private static final String MSG_USER_WITH_EMAIL_NOT_FOUND = "User with email %s not found";
	private static final String MSG_USER_ALREADY_EXISTS = "There is already a user registered with that email %s";
	private static final String MSG_ADDRESS_IN_USE = "Address with ID %s cannot be removed, because it is in use";
	private static final String MSG_NO_MAIN_ADDRESS = "User with the ID %s dont have a main address";
	private static final String MSG_ADDRESS_ALREADY_EXISTS = "There is already a address registered with that nick %s";

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AddressMapper addressMapper;

	private UUID defaultUserRoleId = UUID.fromString("18aace1e-f36a-4d71-b4d1-124387d9b63a");

	@Transactional(readOnly = true)
	public Page<User> list(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		userRepository.findWithRoles(users.toList());
		return users;
	}

	@Transactional(readOnly = true)
	public User findById(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		user.getRoles().isEmpty(); // Force fetch Roles
		return user;
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		User user = userRepository
				.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(String.format(MSG_USER_WITH_EMAIL_NOT_FOUND, email)));
		user.getRoles().isEmpty(); // Force fetch Roles
		return user;
	}

	@Transactional
	public User insert(UserInsert userInsert) {
		validateUniqueInsert(userInsert);
		User user = userMapper.toUser(userInsert);
		user.setPassword(passwordEncoder.encode(userInsert.getPassword()));
		Role defaultRole = roleService.findById(defaultUserRoleId);
		user.getRoles().add(defaultRole);
		userRepository.save(user);
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
		return user;
	}

	@Transactional
	public User update(UUID userId, UserUpdate userUpdate) {
		User user = findById(userId);
		userMapper.update(userUpdate, user);
		return userRepository.save(user);
	}

	public void delete(UUID userId) {
		try {
			userRepository.deleteById(userId);
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_USER_IN_USE, userId));
		}
	}

	@Transactional(readOnly = true)
	public Long count() {
		return userRepository.count();
	}

	@Transactional
	public void changePassword(UUID userId, UserChangePasswordInput userChangePasswordInput) {
		User user = findById(userId);
		user.setPassword(passwordEncoder.encode(userChangePasswordInput.getNewPassword()));
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public Page<Address> listAddresses(UUID userId, Pageable pageable) {
		return addressRepository.findAllByUserId(userId, pageable);
	}

	@Transactional(readOnly = true)
	public Address findAddressById(UUID userId, UUID addressId) {
		return addressRepository
				.findByUserIdAndId(userId, addressId)
				.orElseThrow(() -> new AddressNotFoundException(addressId));
	}

	@Transactional(readOnly = true)
	public Address findMainAddress(UUID userId) {
		Optional<Address> optional = addressRepository.findFirstByUserIdAndMain(userId, true);
		return optional.orElseThrow(() -> new AddressNotFoundException(String.format(MSG_NO_MAIN_ADDRESS, userId)));
	}

	@Transactional
	public Address insertAddress(UUID userId, AddressInput addressInput) {
		validateUniqueAddressInsert(userId, addressInput);
		Address address = addressMapper.toAddress(addressInput);
		User user = findById(userId);
		address.setUser(user);
		address.setMain(false);
		return addressRepository.save(address);
	}

	@Transactional
	public Address updateAddress(UUID userId, UUID addressId, AddressInput addressInput) {
		validateUniqueAddressUpdate(userId, addressId, addressInput);
		Address address = findAddressById(userId, addressId);
		addressMapper.update(addressInput, address);
		return addressRepository.save(address);
	}

	@Transactional
	public void deleteAddress(UUID userId, UUID addressId) {
		try {
			findAddressById(userId, addressId);
			addressRepository.deleteById(addressId);
		} catch (EmptyResultDataAccessException e) {
			throw new AddressNotFoundException(addressId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ADDRESS_IN_USE, addressId));
		}
	}

	@Transactional
	public void setMainAddress(UUID userId, UUID addressId) {
		Address address = findAddressById(userId, addressId);
		if (Boolean.TRUE.equals(address.getMain())) {
			return;
		}
		unsetMainAddress(userId);
		address.setMain(true);
		addressRepository.save(address);
	}

	@Transactional
	public void unsetMainAddress(UUID userId) {
		Address address;
		address = findMainAddress(userId);
		address.setMain(false);
		addressRepository.save(address);
	}

	@Transactional
	public Set<Role> listRoles(UUID userId) {
		User user = findById(userId);
		return user.getRoles();
	}

	@Transactional
	public void grantRole(UUID userId, UUID roleId) {
		User user = findById(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Transactional
	public void revokeRole(UUID userId, UUID roleId) {
		User user = findById(userId);
		Role role = roleService.findById(roleId);
		user.getRoles().remove(role);
		userRepository.save(user);
	}

	private void validateUniqueInsert(UserInsert userInsert) {
		Optional<User> optional = userRepository.findByEmail(userInsert.getEmail());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_USER_ALREADY_EXISTS, userInsert.getName()));
		}
	}

	private void validateUniqueAddressInsert(UUID userId, AddressInput addressInput) {
		Optional<Address> optional = addressRepository.findByUserIdAndNick(userId, addressInput.getNick());
		if (optional.isPresent()) {
			throw new UniqueException(String.format(MSG_ADDRESS_ALREADY_EXISTS, addressInput.getNick()));
		}
	}

	private void validateUniqueAddressUpdate(UUID userId, UUID addressId, AddressInput addressInput) {
		Optional<Address> optional = addressRepository.findByUserIdAndNick(userId, addressInput.getNick());
		if (optional.isPresent() && !optional.get().getId().equals(addressId)) {
			throw new UniqueException(String.format(MSG_ADDRESS_ALREADY_EXISTS, addressInput.getNick()));
		}
	}
}
