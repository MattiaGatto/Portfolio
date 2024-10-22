package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.Carrello;
import tech.ecommerce.repo.carrello_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class CarrelloService {
    private final carrello_repo carrello_repo;

    @Autowired
    public CarrelloService(carrello_repo carrello_repo) {
        this.carrello_repo = carrello_repo;
    }

    public Carrello addCarrello(Carrello carrello){
        Random r=new Random();
        carrello.setId(r.nextLong());
        return carrello_repo.save(carrello);
    }

    public List<Carrello> findCarrelli(){
        return carrello_repo.findAll();
    }

    public Carrello updateCarrello(Carrello c){
        return carrello_repo.save(c);
    }

    public void deleteCarrello(Long id){
        carrello_repo.deleteCarrelloById(id);
    }

    public Carrello findCarrelloById(Long id){
        return carrello_repo.findCarrelloById(id).orElseThrow(() -> new NotFoundException("Carrello : "+id+ " was not found"));
    }

    public Carrello findByEmail(String email){
        return carrello_repo.findByEmail(email).orElseThrow(() -> new NotFoundException("Carrello : "+email+ " was not found"));
    }

    public Carrello findByIdAndEmail(Long id, String email) {
        return carrello_repo.findByIdAndEmail(id,email).orElseThrow(() -> new NotFoundException("Carrello : "+email+ " was not found"));
    }

    public void deleteByIdAndEmail(Long id, String email) {
        carrello_repo.deleteByIdAndEmail(id,email);
    }
}
