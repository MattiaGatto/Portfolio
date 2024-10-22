package tech.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tech.ecommerce.model.Carrello;

@Entity
@Table(name = "dettagliocarrello")
public class DettaglioCarrello implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "carrelloid")
    private Carrello carrello;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "productid")
    private Product product;

    public DettaglioCarrello(Carrello carrello,Product product,int quantity) {
        this.carrello = carrello;
        this.product=product;
        this.quantity=quantity;
    }

    public DettaglioCarrello() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DettaglioCarrello)) return false;
        DettaglioCarrello that = (DettaglioCarrello) o;
        return getQuantity() == that.getQuantity() && Objects.equals(getId(), that.getId()) && Objects.equals(getCarrello(), that.getCarrello()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getCarrello(), getProduct());
    }

    @Override
    public String toString() {
        return "DettaglioCarrello{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", carrello=" + carrello +
                ", product=" + product +
                '}';
    }
}
