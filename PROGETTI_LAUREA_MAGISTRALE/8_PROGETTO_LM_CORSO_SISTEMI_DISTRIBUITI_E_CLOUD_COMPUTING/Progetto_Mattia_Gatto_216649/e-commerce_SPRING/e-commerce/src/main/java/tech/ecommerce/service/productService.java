package tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ecommerce.exception.NotFoundException;
import tech.ecommerce.model.Product;
import tech.ecommerce.repo.product_repo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class productService {

    private final product_repo product_repo;

    @Autowired
    public productService(product_repo product) {
        this.product_repo = product;
    }


    public Product addProduct(Product product){
        Random r=new Random();
        product.setId(r.nextLong());
        return product_repo.save(product);
    }

    public List<Product> findAllProduct(){
        return product_repo.findAll();
    }

    public Product updateProduct(Product p){
        return product_repo.save(p);
    }

    public void deleteProduct(Long id){
        product_repo.deleteProductById(id);
    }

    public Product findProductById(Long id){
        return product_repo.findProductById(id).orElseThrow(() -> new NotFoundException("Product : "+id+ " was not found"));
    }

}
