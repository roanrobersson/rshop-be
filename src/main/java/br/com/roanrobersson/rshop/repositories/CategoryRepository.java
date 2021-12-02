package br.com.roanrobersson.rshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);
}
