package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.Address;
import tech.ecommerce.model.PlaceOrder;
import tech.ecommerce.model.User;
import tech.ecommerce.repo.address_repo;
import tech.ecommerce.repo.placeOrder_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class addressService {

    private final address_repo address_repo;

    @Autowired
    public addressService(address_repo address_repo) {
        this.address_repo = address_repo;
    }



    public Address addAddress(Address address){
        Random r=new Random();
        address.setId(r.nextLong());
        return address_repo.save(address);
    }

    public List<Address> findAllAddress(){
        return address_repo.findAll();
    }

    public Address updateAddress(Address a){
        return address_repo.save(a);
    }

    public void deleteAddress(Long id){
        address_repo.deleteAddressById(id);
    }

    public Address findAddressById(Long id){
        return address_repo.findAddressById(id).orElseThrow(() -> new NotFoundException("Address : "+id+ " was not found"));
    }

}
