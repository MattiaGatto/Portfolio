package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.Address;
import tech.ecommerce.model.User;

import java.util.Optional;

@Repository
public interface address_repo extends JpaRepository<Address,Long> {
    void deleteAddressById(Long id);

    Optional<Address> findAddressById(Long id);

}
