package br.com.roanrobersson.rshop.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.roanrobersson.rshop.dto.address.AddressInsertDTO;
import br.com.roanrobersson.rshop.dto.address.AddressResponseDTO;
import br.com.roanrobersson.rshop.dto.address.AddressUpdateDTO;
import br.com.roanrobersson.rshop.entities.Address;
import br.com.roanrobersson.rshop.entities.User;
import br.com.roanrobersson.rshop.repositories.AddressRepository;
import br.com.roanrobersson.rshop.repositories.UserRepository;
import br.com.roanrobersson.rshop.services.exceptions.DatabaseException;
import br.com.roanrobersson.rshop.services.exceptions.ResourceNotFoundException;

@Service
public class UserAddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public Page<AddressResponseDTO> findAllPaged(Long userId, PageRequest pageRequest) {
		authService.validateSelfOrOperatorOrAdmin(userId);
		Page<Address> list = addressRepository.findAllByUserId(userId, pageRequest);
		return list.map(x -> new AddressResponseDTO(x));
	}
	
	@Transactional(readOnly = true)
	public AddressResponseDTO findById(Long userId, Long addressId) {
		authService.validateSelfOrOperatorOrAdmin(userId);
		Optional<Address> obj = addressRepository.findById(addressId);
		Address entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id " + addressId + " not found"));
		if (userId != entity.getUser().getId()) throw new ResourceNotFoundException("Id " + addressId + " not found");
		return new AddressResponseDTO(entity);
	}

	@Transactional
	public AddressResponseDTO insert(Long userId, AddressInsertDTO dto) {
		authService.validateSelfOrOperatorOrAdmin(userId);
		try {
			User userEntity = userRepository.getById(userId);
			Address addressEntity = modelMapper.map(dto, Address.class);
			addressEntity.setUser(userEntity);
			addressEntity = addressRepository.save(addressEntity);
			return new AddressResponseDTO(addressEntity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("User id " + userId + " not found ");
		}
	}
	
	@Transactional
	public AddressResponseDTO update(Long userId, Long addressId, AddressUpdateDTO dto) {
		authService.validateSelfOrOperatorOrAdmin(userId);
			Optional<Address> obj = addressRepository.findById(addressId);
			Address addressEntity = obj.orElseThrow(() -> new ResourceNotFoundException("Id " + addressId + " not found"));
			if (userId != addressEntity.getUser().getId()) throw new ResourceNotFoundException("Id " + addressId + " not found");
			modelMapper.map(dto, addressEntity);
			addressEntity = addressRepository.save(addressEntity);
			return new AddressResponseDTO(addressEntity);
	}
	
	public void delete(Long userId, Long addressId) {
		authService.validateSelfOrOperatorOrAdmin(userId);
		try {
			Optional<Address> obj = addressRepository.findById(addressId);
			Address entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id " + addressId + " not found"));
			if (userId != entity.getUser().getId()) throw new ResourceNotFoundException("Id " + addressId + " not found");
			addressRepository.deleteById(addressId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + addressId + " not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
