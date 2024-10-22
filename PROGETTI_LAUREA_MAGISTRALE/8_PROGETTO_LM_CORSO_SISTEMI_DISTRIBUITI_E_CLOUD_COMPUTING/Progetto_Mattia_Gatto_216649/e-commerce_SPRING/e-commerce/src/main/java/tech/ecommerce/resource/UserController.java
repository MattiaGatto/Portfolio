package tech.ecommerce.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.*;
import tech.ecommerce.service.*;

import javax.persistence.NonUniqueResultException;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class.getName());


    @Autowired
    private addressService  addressService;


    @GetMapping("/address/all")
    public ResponseEntity<List<Address>> getAllAddress(){
        List<Address> addresses=addressService.findAllAddress();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/address/find/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable("id") Long a){
         try{
            Address address=addressService.findAddressById(a);
            return new ResponseEntity<>(address, HttpStatus.OK);
         }catch(NonUniqueResultException n){ return new ResponseEntity<Address>(new Address(),HttpStatus.NOT_FOUND);}
         catch(NotFoundException n){ return new ResponseEntity<Address>(new Address(),HttpStatus.NOT_FOUND);}

    }



    @PostMapping("/address/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address a){
        Address newAddress=addressService.addAddress(a);
        return new ResponseEntity<>(newAddress, HttpStatus.OK);
    }

    @PutMapping("/address/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address a){
        Address updateAddress=addressService.updateAddress(a);
        return new ResponseEntity<>(updateAddress, HttpStatus.OK);
    }

    @DeleteMapping("/address/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id")Long a){
        addressService.deleteAddress(a);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private CarrelloService carrelloService;

    @GetMapping("/carrello/all")
    public ResponseEntity<List<Carrello>> getAllCarrello(){
        List<Carrello> carrelli=carrelloService.findCarrelli();
        return new ResponseEntity<>(carrelli, HttpStatus.OK);
    }

    @GetMapping("/carrello/find/{id}")
    public ResponseEntity<Carrello> getCarrelloById(@PathVariable("id") Long c){
        try{
            Carrello cart=carrelloService.findCarrelloById(c);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<Carrello>(new Carrello(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<Carrello>(new Carrello(),HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/carrello/finde/{email}")
    public ResponseEntity<Carrello>  findByEmail(@PathVariable("email")String email){
        try {
            Carrello cart=carrelloService.findByEmail(email);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<Carrello>(new Carrello(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<Carrello>(new Carrello(),HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/carrello/findc/{id}/{email}")
    public ResponseEntity<Carrello>  findByIdAndEmail(@PathVariable("id") Long id,@PathVariable("email") String email) {
        try {
            Carrello cart=carrelloService.findByIdAndEmail(id,email);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<Carrello>(new Carrello(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<Carrello>(new Carrello(),HttpStatus.NOT_FOUND);}

    }

    @PostMapping("/carrello/add")
    public ResponseEntity<Carrello> addCarrello(@RequestBody Carrello c){
        Carrello newc=carrelloService.addCarrello(c);
        return new ResponseEntity<>(newc, HttpStatus.OK);
    }

    @PutMapping("/carrello/update")
    public ResponseEntity<Carrello> updateCarrello(@RequestBody Carrello c){
        Carrello updateCar=carrelloService.updateCarrello(c);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }

    @PutMapping("/carrello/updateProd/{idprod}")
    public ResponseEntity<Carrello> updateCarrelloProd(@RequestBody Carrello carello,@PathVariable("idprod")Long idprod){
        Carrello updateCar= carrelloService.updateCarrello(carello);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }


    @DeleteMapping("/carrello/delete/{id}")
    public ResponseEntity<?> deleteCarrello(@PathVariable("id")Long id){
        carrelloService.deleteCarrello(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @DeleteMapping("/carrello/delete/{id}/{email}")
    public  ResponseEntity<?> deleteByIdAndEmail(@PathVariable("id")Long id,@PathVariable("email")String email){
        carrelloService.deleteByIdAndEmail(id,email);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private PlaceOrderService placeOrderService;

    @GetMapping("/placeorder/all")
    public ResponseEntity<List<PlaceOrder>> getAllOrder(){
        List<PlaceOrder> orders=placeOrderService.findAllPlaceOrder();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/placeorder/find/{id}")
    public ResponseEntity<PlaceOrder> getOrderById(@PathVariable("id") Long o){
        try{
            PlaceOrder order=placeOrderService.findPlaceOrderById(o);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<PlaceOrder>(new PlaceOrder(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<PlaceOrder>(new PlaceOrder(),HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/placeorder/finde/{email}")
    public ResponseEntity<List<PlaceOrder>> findPlaceOrderByUserEmail(@PathVariable("email") String email){
        List<PlaceOrder> order=placeOrderService.findPlaceOrderByUserEmail(email);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/placeorder/add")
    public ResponseEntity<PlaceOrder> addOrder(@RequestBody PlaceOrder o){
        PlaceOrder neworder=placeOrderService.addPlaceOrder(o);
        return new ResponseEntity<>(neworder, HttpStatus.OK);
    }

    @PutMapping("/placeorder/update")
    public ResponseEntity<PlaceOrder> updateOrder(@RequestBody PlaceOrder o){
        PlaceOrder updateOrder=placeOrderService.updatePlaceOrder(o);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping("/placeorder/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id")Long o){
        placeOrderService.deletePlaceOrder(o);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private productService productService;

    @GetMapping("/product/all")
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> products=productService.findAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/find/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long p){
        try {
            Product product=productService.findProductById(p);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<Product>(new Product(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<Product>(new Product(),HttpStatus.NOT_FOUND);}

    }

    @PostMapping("/product/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product p){
        Product newProduct=productService.addProduct(p);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @PutMapping("/product/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product p){
        Product updateProduct=productService.updateProduct(p);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id")Long p){
        productService.deleteProduct(p);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private UserService userService;


    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users=userService.findAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        try{
            User user=userService.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/user/findu/{email}/{password}/{usertype}")
    public ResponseEntity<User> getUserByEmailPasswordUsertype(@PathVariable("email") String email,
                                                               @PathVariable("password") String password,
                                                               @PathVariable("usertype") String usertype) {
        try {
            User user = userService.findByEmailAndPasswordAndUsertype(email, password, usertype);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(NonUniqueResultException n){ return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/user/finde/{email}/{usertype}")
    public ResponseEntity<User> getUserByEmailUsertype(@PathVariable("email") String email,
                                                       @PathVariable("usertype") String usertype) {
        try {
            User user = userService.getUserByEmailUsertype(email, usertype);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(NonUniqueResultException n){ return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<User>(new User(),HttpStatus.NOT_FOUND);}
    }

    @PostMapping("/user/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        ResponseEntity query=getUserByEmailUsertype(user.getEmail(),user.getUsertype());
        if(query.getStatusCode()==HttpStatus.OK){
            return new ResponseEntity<>((User)query.getBody(), HttpStatus.OK);
        }
        else {
            if(user.getUsertype()==null){
                user.setUsertype("user");
            }

            //aggiorna user
            Address a=addressService.addAddress(new Address("", "", "", "", 0, user.getPhone(),user.getEmail(),user.getNome(),user.getCognome()));
            ArrayList<Product> y= new ArrayList<Product>();
            Carrello c=carrelloService.addCarrello(new Carrello(user.getEmail(),0.0));
            user.setCarrello(c);
            user.setAddress(a);
            User newUser =  userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updateUser=userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private DettaglioCarrelloService dettaglioCarrelloService;


    @GetMapping("/dettagliocarrello/all")
    public ResponseEntity<List<DettaglioCarrello>> getAllDettaglioCarrello(){
        List<DettaglioCarrello> users=dettaglioCarrelloService.findAllDettaglioCarrello();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/dettagliocarrello/find/{id}")
    public ResponseEntity<DettaglioCarrello> getAllDettaglioCarrelloById(@PathVariable("id") Long id){
        try{
            DettaglioCarrello d=dettaglioCarrelloService.findDettaglioCarrelloById(id);
            return new ResponseEntity<>(d, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<DettaglioCarrello>(new DettaglioCarrello(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<DettaglioCarrello>(new DettaglioCarrello(),HttpStatus.NOT_FOUND);}

    }

    @PostMapping("/dettagliocarrello/findc")
    public ResponseEntity<List<DettaglioCarrello>> getAllDettaglioCarrelloByCarrello(@RequestBody Carrello c){
        List<DettaglioCarrello> d=dettaglioCarrelloService.findAllDettaglioCarrelloByCarrello(c);
        return new ResponseEntity<>(d, HttpStatus.OK);

    }

    @PostMapping("/dettagliocarrello/add")
    public ResponseEntity<DettaglioCarrello> addDettaglioCarrello(@RequestBody DettaglioCarrello d){
        DettaglioCarrello det= dettaglioCarrelloService.updateDettaglioCarrello(d);
        System.out.println(det.getProduct().getQuantity());
        return new ResponseEntity<>(det, HttpStatus.OK);
    }

    @PutMapping("/dettagliocarrello/update")
    public ResponseEntity<DettaglioCarrello> updateDettaglioCarrello(@RequestBody DettaglioCarrello d){
        DettaglioCarrello updateDet=dettaglioCarrelloService.updateDettaglioCarrello(d);
        return new ResponseEntity<>(updateDet, HttpStatus.OK);
    }

    @DeleteMapping("/dettagliocarrello/delete/{id}")
    public ResponseEntity<?> deleteDettaglioCarrello(@PathVariable("id")Long id){
        dettaglioCarrelloService.deleteDettaglioCarrello(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private creditcardService creditcardService;


    @GetMapping("/creditcard/all")
    public ResponseEntity<List<CreditCard>> getAllCreditCard(){
        List<CreditCard> users=creditcardService.findAllCreditCard();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/creditcard/find/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable("id") Long id){
        try{
            CreditCard d=creditcardService.findCreditCardById(id);
            return new ResponseEntity<>(d, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<CreditCard>(new CreditCard(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<CreditCard>(new CreditCard(),HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/creditcard/findcc/{creditnumber}/{creditcode}")
    public ResponseEntity<CreditCard> findByNumberCodeData(@PathVariable("creditnumber") String creditnumber,@PathVariable("creditcode")String creditcode){
        try{
            CreditCard d=creditcardService.findByNumberCodeData(creditnumber,creditcode);
            return new ResponseEntity<>(d, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<CreditCard>(new CreditCard(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<CreditCard>(new CreditCard(),HttpStatus.NOT_FOUND);}
    }

    @GetMapping("/creditcard/findce/{email}")
    public ResponseEntity<CreditCard> findCreditCardByEmail(@PathVariable("email") String emailcard){
        try{
            CreditCard d=creditcardService.findCreditCardByEmail(emailcard);
            return new ResponseEntity<>(d, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<CreditCard>(new CreditCard(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<CreditCard>(new CreditCard(),HttpStatus.NOT_FOUND);}
    }

    @PostMapping("/creditcard/add")
    public ResponseEntity<CreditCard> addCreditCard(@RequestBody CreditCard creditCard){
        CreditCard creditc= creditcardService.addCreditCard(creditCard);
        return new ResponseEntity<>(creditc, HttpStatus.OK);
    }

    @PutMapping("/creditcard/update")
    public ResponseEntity<CreditCard> updateCreditCard(@RequestBody CreditCard d){
        CreditCard updateDet=creditcardService.updateCreditCard(d);
        return new ResponseEntity<>(updateDet, HttpStatus.OK);
    }

    @DeleteMapping("/creditcard/delete/{id}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable("id")Long id){
        creditcardService.deleteCreditCard(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Autowired
    private DettaglioOrdineService dettaglioOrdineService;


    @GetMapping("/dettaglioordine/all")
    public ResponseEntity<List<DettaglioOrdine>> getAllDettaglioOrdine(){
        List<DettaglioOrdine> o=dettaglioOrdineService.findAllDettaglioOrdine();
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping("/dettaglioordine/find/{id}")
    public ResponseEntity<DettaglioOrdine> getAllDettaglioOrdineById(@PathVariable("id") Long id){
        try{
            DettaglioOrdine d=dettaglioOrdineService.finddettaglioOrdineById(id);
            return new ResponseEntity<>(d, HttpStatus.OK);
        }catch(NonUniqueResultException n){ return new ResponseEntity<DettaglioOrdine>(new DettaglioOrdine(),HttpStatus.NOT_FOUND);}
        catch(NotFoundException n){ return new ResponseEntity<DettaglioOrdine>(new DettaglioOrdine(),HttpStatus.NOT_FOUND);}

    }

    @PostMapping("/dettaglioordine/findp")
    public ResponseEntity<List<DettaglioOrdine>> getAllDettaglioOrdineByPlaceorder(@RequestBody PlaceOrder p){
        List<DettaglioOrdine> dettaglio=dettaglioOrdineService.findDettaglioOrdineByPlaceorder(p);
        return new ResponseEntity<>(dettaglio, HttpStatus.OK);

    }

    @PostMapping("/dettaglioordine/add")
    public ResponseEntity<DettaglioOrdine> addDettaglioOrdine(@RequestBody DettaglioOrdine d){
        DettaglioOrdine det= dettaglioOrdineService.addDettaglioOrdine(d);
        return new ResponseEntity<>(det, HttpStatus.OK);
    }

    @PutMapping("/dettaglioordine/update")
    public ResponseEntity<DettaglioOrdine> updateDettaglioOrdine(@RequestBody DettaglioOrdine d){
        DettaglioOrdine updateDet=dettaglioOrdineService.updateDettaglioOrdine(d);
        return new ResponseEntity<>(updateDet, HttpStatus.OK);
    }

    @DeleteMapping("/dettaglioordine/delete/{id}")
    public ResponseEntity<?> deleteDettaglioOrdine(@PathVariable("id")Long id){
        dettaglioOrdineService.deletedettaglioOrdine(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
