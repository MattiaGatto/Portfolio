package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface dettaglioOrdine_repo extends JpaRepository<DettaglioOrdine,Long> {

    Optional<DettaglioOrdine> findDettaglioOrdineById(Long id);

    void deleteById(Long id);

    Optional<List<DettaglioOrdine>> findDettaglioOrdineByPlaceorder(PlaceOrder placeorder);
}
