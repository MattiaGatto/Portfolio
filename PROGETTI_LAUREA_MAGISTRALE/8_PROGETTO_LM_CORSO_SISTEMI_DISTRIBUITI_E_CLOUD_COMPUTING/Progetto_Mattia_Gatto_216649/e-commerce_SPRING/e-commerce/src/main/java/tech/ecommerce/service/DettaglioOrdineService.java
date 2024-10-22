package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.DettaglioOrdine;
import tech.ecommerce.model.PlaceOrder;
import tech.ecommerce.repo.dettaglioOrdine_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class DettaglioOrdineService {
    private final tech.ecommerce.repo.dettaglioOrdine_repo dettaglioOrdine_repo;

    @Autowired
    public DettaglioOrdineService(dettaglioOrdine_repo dettaglioOrdine_repo) {
        this.dettaglioOrdine_repo = dettaglioOrdine_repo;
    }

    public DettaglioOrdine addDettaglioOrdine(DettaglioOrdine dettaglioOrdine){
        Random r=new Random();
        dettaglioOrdine.setId(r.nextLong());
        return dettaglioOrdine_repo.save(dettaglioOrdine);
    }

    public List<DettaglioOrdine> findAllDettaglioOrdine(){
        return dettaglioOrdine_repo.findAll();
    }

    public DettaglioOrdine updateDettaglioOrdine(DettaglioOrdine dettaglioOrdine){
        return dettaglioOrdine_repo.save(dettaglioOrdine);
    }

    public void deletedettaglioOrdine(Long id){
        dettaglioOrdine_repo.deleteById(id);
    }

    public DettaglioOrdine finddettaglioOrdineById(Long id){
        return dettaglioOrdine_repo.findDettaglioOrdineById(id).orElseThrow(() -> new NotFoundException("DettaglioOrdine : "+id+ " was not found"));
    }

    public List<DettaglioOrdine> findDettaglioOrdineByPlaceorder(PlaceOrder placeOrder){
        return dettaglioOrdine_repo.findDettaglioOrdineByPlaceorder(placeOrder).orElseThrow(() -> new NotFoundException("DettaglioOrdine : "+placeOrder+ " was not found"));
    }


}
