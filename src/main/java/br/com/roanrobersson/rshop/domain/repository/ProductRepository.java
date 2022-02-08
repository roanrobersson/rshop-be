package br.com.roanrobersson.rshop.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT DISTINCT obj " 
			+ "FROM Product obj " 
			+ "INNER JOIN obj.categories cats "
			+ "WHERE (COALESCE(:categories) IS NULL OR cats.id IN :categories) "
			+ "AND (LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%')) )")
	Page<Product> search(Set<Long> categories, String name, Pageable page);

	@Query("SELECT obj " 
			+ "FROM Product obj " 
			+ "LEFT JOIN FETCH obj.categories " 
			+ "WHERE obj IN :products")
	List<Product> findWithCategories(List<Product> products);

	@Query("SELECT obj " 
			+ "FROM Product obj " 
			+ "LEFT JOIN FETCH obj.categories " 
			+ "WHERE obj.id = :id")
	Optional<Product> findByIdWithCategories(Long id);
	
	Optional<Product> findByName(String name);
}
