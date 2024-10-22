package tech.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ecommerce.model.Carrello;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface carrello_repo extends JpaRepository<Carrello,Long> {
    void deleteCarrelloById(Long id);

    Optional<Carrello> findCarrelloById(Long id);

    Optional<Carrello> findByEmail(String email);

    Optional<Carrello> findByIdAndEmail(Long id, String email);

    void deleteByIdAndEmail(Long id, String email);
}
