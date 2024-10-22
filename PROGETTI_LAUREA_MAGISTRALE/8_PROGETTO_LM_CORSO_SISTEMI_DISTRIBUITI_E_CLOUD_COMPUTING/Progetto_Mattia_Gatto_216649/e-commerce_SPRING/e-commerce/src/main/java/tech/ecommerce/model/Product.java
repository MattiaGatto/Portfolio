package tech.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String description,name,imageurl;
    private double price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "carId")
    private Carrello carrello;

    public Product() {}

    public Product( String description, String name, double price, int quantity,String imageurl) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageurl=imageurl;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long productid) {
        this.id = productid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 && getQuantity() == product.getQuantity() && Objects.equals(getId(), product.getId()) && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getName(), product.getName()) && Objects.equals(getImageurl(), product.getImageurl()) && Objects.equals(getCarrello(), product.getCarrello());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getName(), getImageurl(), getPrice(), getQuantity(), getCarrello());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", carrello=" + carrello +
                '}';
    }
}