package tech.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carrello")
public class Carrello implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String email;

    private double price;


    public Carrello() {}

    public Carrello(String email, double price) {
        this.email = email;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carrello)) return false;
        Carrello carrello = (Carrello) o;
        return Double.compare(carrello.getPrice(), getPrice()) == 0 && Objects.equals(getId(), carrello.getId()) && Objects.equals(getEmail(), carrello.getEmail()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPrice());
    }

    @Override
    public String toString() {
        return "Carrello{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", price=" + price +
                '}';
    }
}
