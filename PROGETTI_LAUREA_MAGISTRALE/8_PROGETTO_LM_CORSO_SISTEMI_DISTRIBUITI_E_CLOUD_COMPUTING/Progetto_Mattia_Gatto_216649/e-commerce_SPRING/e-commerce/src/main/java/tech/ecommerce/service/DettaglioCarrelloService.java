package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.Carrello;
import tech.ecommerce.model.DettaglioCarrello;
import tech.ecommerce.model.Product;
import tech.ecommerce.repo.dettaglioCarrello_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class DettaglioCarrelloService {
    private final dettaglioCarrello_repo dettaglioCarrello_repo;

    @Autowired
    public DettaglioCarrelloService(dettaglioCarrello_repo dettaglioCarrello_repo) {
        this.dettaglioCarrello_repo = dettaglioCarrello_repo;
    }

    public DettaglioCarrello addDettaglioCarrello(DettaglioCarrello dettaglioCarrello){
        Random r=new Random();
        dettaglioCarrello.setId(r.nextLong());
        return dettaglioCarrello_repo.save(dettaglioCarrello);
    }

    public List<DettaglioCarrello> findAllDettaglioCarrello(){
        return dettaglioCarrello_repo.findAll();
    }

    public DettaglioCarrello updateDettaglioCarrello(DettaglioCarrello d){
        return dettaglioCarrello_repo.save(d);
    }

    public void deleteDettaglioCarrello(Long id){
        dettaglioCarrello_repo.deleteById(id);
    }

    public DettaglioCarrello findDettaglioCarrelloById(Long id){
        return dettaglioCarrello_repo.findDettaglioCarrelloById(id).orElseThrow(() -> new NotFoundException("Carrello : "+id+ " was not found"));
    }

    public List<DettaglioCarrello> findAllDettaglioCarrelloByCarrello(Carrello c){
        return dettaglioCarrello_repo.findAllDettaglioCarrelloByCarrello(c).orElseThrow(() -> new NotFoundException("Carrello : "+c+ " was not found"));
    }
}
