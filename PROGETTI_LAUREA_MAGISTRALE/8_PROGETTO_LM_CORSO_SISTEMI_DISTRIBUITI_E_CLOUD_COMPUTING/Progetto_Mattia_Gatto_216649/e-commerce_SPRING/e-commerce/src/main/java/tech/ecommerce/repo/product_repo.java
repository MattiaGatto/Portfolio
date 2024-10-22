package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.Product;
import tech.ecommerce.model.User;

import java.util.Optional;

@Repository
public interface product_repo extends JpaRepository<Product,Long> {
    void deleteProductById(Long id);

    Optional<Product> findProductById(Long productid);
}
