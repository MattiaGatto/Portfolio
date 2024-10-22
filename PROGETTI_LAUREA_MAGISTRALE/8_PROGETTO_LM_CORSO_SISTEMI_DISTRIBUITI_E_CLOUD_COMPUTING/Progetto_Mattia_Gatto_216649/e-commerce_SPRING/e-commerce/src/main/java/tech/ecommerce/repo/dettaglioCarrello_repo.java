package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.Carrello;
import tech.ecommerce.model.DettaglioCarrello;
import tech.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface dettaglioCarrello_repo extends JpaRepository<DettaglioCarrello,Long> {

    Optional<DettaglioCarrello> findDettaglioCarrelloById(Long id);

    void deleteById(Long id);

    Optional<List<DettaglioCarrello>> findAllDettaglioCarrelloByCarrello(Carrello c);

    Optional<DettaglioCarrello> findAllDettaglioCarrelloByProduct(Product p);
}
