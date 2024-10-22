package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.CreditCard;
import tech.ecommerce.model.User;
import tech.ecommerce.repo.creditcard_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class creditcardService {

    private final creditcard_repo creditcard_repo;

    @Autowired
    public creditcardService(creditcard_repo creditcard_repo){this.creditcard_repo=creditcard_repo;}

    public CreditCard addCreditCard(CreditCard creditCard){
        Random r=new Random();
        creditCard.setId(r.nextLong());
        return creditcard_repo.save(creditCard);
    }
    public List<CreditCard> findAllCreditCard(){
        return creditcard_repo.findAll();
    }

    public CreditCard updateCreditCard(CreditCard c){
        return creditcard_repo.save(c);
    }

    public void deleteCreditCard(Long id){
        creditcard_repo.deleteAddressById(id);
    }

    public CreditCard findCreditCardById(Long id){
        return creditcard_repo.findCreditCardById(id).orElseThrow(() -> new NotFoundException("CreditCard : "+id+ " was not found"));
    }
    public CreditCard findByNumberCodeData(String creditnumber,String creditcode){
        return creditcard_repo.findByCreditnumberAndCreditcode(creditnumber,creditcode).orElseThrow(() -> new NotFoundException("CreditCard : "+creditnumber+ " was not found"));
    }

    public CreditCard findCreditCardByEmail(String emailcard) {
        return creditcard_repo.findByEmailcard(emailcard).orElseThrow(() -> new NotFoundException("CreditCard : "+emailcard+ " was not found"));
    }
}
