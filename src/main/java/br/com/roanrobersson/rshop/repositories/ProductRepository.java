package br.com.roanrobersson.rshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.roanrobersson.rshop.entities.Category;
import br.com.roanrobersson.rshop.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT DISTINCT obj " 
			+ "FROM Product obj " 
			+ "INNER JOIN obj.categories cats "
			+ "WHERE (COALESCE(:categories) IS NULL OR cats IN :categories) "
			+ "AND (LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%')) )")
	Page<Product> search(List<Category> categories, String name, Pageable page);

	@Query("SELECT obj " 
			+ "FROM Product obj " 
			+ "JOIN FETCH obj.categories " 
			+ "WHERE obj IN :products")
	List<Product> findProductsWithCategories(List<Product> products);

	Optional<Product> findByName(String name);
}
