package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.User;
import tech.ecommerce.repo.user_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserService {

    private final user_repo user;

    @Autowired
    public UserService(user_repo user) {
        this.user = user;
    }

    public User addUser(User u){
        Random r=new Random();
        u.setId(r.nextLong());
        return user.save(u);
    }

    public List<User> findAllUser(){
        return user.findAll();
    }

    public User updateUser(User u){
        return user.save(u);
    }

    public void deleteUser(Long id){
        user.deleteUserById(id);
    }

    public User findUserById(Long id){
        return user.findUserById(id).orElseThrow(() -> new NotFoundException("User : "+id+ " was not found"));
    }

    public User findByEmailAndPasswordAndUsertype(String email, String password, String usertype){
        return user. findByEmailAndPasswordAndUsertype(email,password,usertype).orElseThrow(() -> new NotFoundException("User : "+email+ " was not found"));
    }

    public User getUserByEmailUsertype(String email, String usertype) {
        return user. findByEmailAndUsertype(email,usertype).orElseThrow(() -> new NotFoundException("User : "+email+ " was not found"));
    }
}
