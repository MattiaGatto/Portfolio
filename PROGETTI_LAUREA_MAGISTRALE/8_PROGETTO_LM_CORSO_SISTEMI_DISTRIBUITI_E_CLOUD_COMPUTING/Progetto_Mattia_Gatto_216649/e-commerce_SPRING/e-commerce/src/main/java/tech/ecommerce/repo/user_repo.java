package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ecommerce.model.User;

import java.util.Optional;

public interface user_repo extends JpaRepository<User,Long> {
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);

    Optional<User> findByEmailAndPasswordAndUsertype(String email, String password, String usertype);

    Optional<User> findByEmailAndUsertype(String email, String usertype);
}
