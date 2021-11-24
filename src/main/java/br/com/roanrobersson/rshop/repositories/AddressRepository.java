package br.com.roanrobersson.rshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Page<Address> findAllByUserId(Long userId, Pageable page);

	Address findByNick(String nick);
}
