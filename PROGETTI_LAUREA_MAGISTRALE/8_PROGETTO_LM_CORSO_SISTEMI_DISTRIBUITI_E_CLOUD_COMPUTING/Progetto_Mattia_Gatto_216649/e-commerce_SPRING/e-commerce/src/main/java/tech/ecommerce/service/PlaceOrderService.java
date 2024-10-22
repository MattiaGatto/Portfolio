package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.PlaceOrder;
import tech.ecommerce.repo.placeOrder_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PlaceOrderService {

    private final placeOrder_repo placeOrder_repo;

    @Autowired
    public PlaceOrderService(placeOrder_repo placeOrder_repo) {
        this.placeOrder_repo = placeOrder_repo;
    }


    public PlaceOrder addPlaceOrder(PlaceOrder placeOrder){
        Random r=new Random();
        placeOrder.setId(r.nextLong());
        return placeOrder_repo.save(placeOrder);
    }

    public List<PlaceOrder> findAllPlaceOrder(){
        return placeOrder_repo.findAll();
    }

    public PlaceOrder updatePlaceOrder(PlaceOrder p){
        return placeOrder_repo.save(p);
    }

    public void deletePlaceOrder(Long id){
        placeOrder_repo.deletePlaceOrderById(id);
    }

    public PlaceOrder findPlaceOrderById(Long id){
        return placeOrder_repo.findPlaceOrderById(id).orElseThrow(() -> new NotFoundException("Ordine : "+id+ " was not found"));
    }
    public List<PlaceOrder> findPlaceOrderByUserEmail(String useremail){
        return placeOrder_repo.findPlaceOrderByUseremail(useremail).orElseThrow(() -> new NotFoundException("Ordine : "+useremail+ " was not found"));
    }

}
