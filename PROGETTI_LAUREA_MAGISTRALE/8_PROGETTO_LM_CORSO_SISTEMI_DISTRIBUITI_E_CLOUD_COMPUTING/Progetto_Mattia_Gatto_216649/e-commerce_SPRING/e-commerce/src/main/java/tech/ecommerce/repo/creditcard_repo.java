package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.Address;
import tech.ecommerce.model.CreditCard;
import tech.ecommerce.model.User;

import java.util.Optional;

@Repository
public interface creditcard_repo extends JpaRepository<CreditCard,Long> {
    void deleteAddressById(Long id);

    Optional<CreditCard> findCreditCardById(Long id);

    Optional<CreditCard> findByCreditnumberAndCreditcode(String creditnumber, String expiration_date);

    Optional<CreditCard> findByEmailcard(String emailcard);
}
