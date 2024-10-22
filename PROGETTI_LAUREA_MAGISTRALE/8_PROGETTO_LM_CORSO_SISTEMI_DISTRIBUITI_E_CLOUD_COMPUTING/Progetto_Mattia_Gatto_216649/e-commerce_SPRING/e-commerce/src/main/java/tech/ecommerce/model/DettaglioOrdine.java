package tech.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dettaglioordine")
public class DettaglioOrdine {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "placeorderid")
    private PlaceOrder placeorder;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "productid")
    private Product product;

    public DettaglioOrdine(){}

    public DettaglioOrdine(int quantità, PlaceOrder placeorder, Product product) {
        this.quantity = quantità;
        this.placeorder = placeorder;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PlaceOrder getPlaceorder() {
        return placeorder;
    }

    public void setPlaceorder(PlaceOrder placeorder) {
        this.placeorder = placeorder;
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
        if (!(o instanceof DettaglioOrdine)) return false;
        DettaglioOrdine that = (DettaglioOrdine) o;
        return quantity==that.quantity && Objects.equals(id, that.id) && Objects.equals(placeorder, that.placeorder) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, placeorder, product);
    }

    @Override
    public String toString() {
        return "DettaglioOrdine{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", placeorder=" + placeorder +
                ", product=" + product +
                '}';
    }
}
