package br.com.roanrobersson.rshop.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT obj FROM User obj " //
			+ "LEFT JOIN FETCH obj.roles roles " //
			+ "WHERE obj IN :users")
	List<User> findWithRoles(List<User> users);

	Optional<User> findByEmail(String email);

	@Query("SELECT obj FROM User obj " //
			+ "LEFT JOIN FETCH obj.roles roles " //
			+ "LEFT JOIN FETCH roles.privileges " //
			+ "WHERE obj.email = :email")
	Optional<User> findByEmailWithRolesAndPrivileges(String email);

	void deleteById(Long userId);
}
