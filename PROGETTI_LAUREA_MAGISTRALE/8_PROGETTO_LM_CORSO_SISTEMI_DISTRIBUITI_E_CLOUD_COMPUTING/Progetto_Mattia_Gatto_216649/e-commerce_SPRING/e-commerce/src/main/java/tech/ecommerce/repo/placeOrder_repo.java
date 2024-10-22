package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.PlaceOrder;
import tech.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface placeOrder_repo extends JpaRepository<PlaceOrder,Long> {
    void deletePlaceOrderById(Long id);

    Optional<PlaceOrder> findPlaceOrderById(Long id);

    Optional<List<PlaceOrder>> findPlaceOrderByUseremail(String useremail);
}
