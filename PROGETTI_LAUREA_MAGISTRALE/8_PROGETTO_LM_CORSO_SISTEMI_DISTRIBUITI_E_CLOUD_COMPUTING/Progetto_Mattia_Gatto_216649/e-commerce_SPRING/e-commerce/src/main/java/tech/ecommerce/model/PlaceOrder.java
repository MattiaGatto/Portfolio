package tech.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "placeorder")
public class PlaceOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String email, orderStatus;
    private Date orderDate;
    private double totalCost;
    private String address;
    private String city;
    private String state;
    private String country;
    private int zipcode;
    private String phonenumber;
    private String nome;
    private String cognome;
    private String description;
    private String useremail;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "creditcardid")
    private CreditCard creditCard;

    public PlaceOrder(String email, String orderStatus, Date orderDate, double totalCost, String address, String city, String state, String country, int zipcode, String phonenumber, String nome, String cognome, String description, CreditCard creditCard,String useremail) {
        this.email = email;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
        this.nome = nome;
        this.cognome = cognome;
        this.description = description;
        this.creditCard = creditCard;
        this.useremail=useremail;
    }
    public PlaceOrder(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceOrder)) return false;
        PlaceOrder that = (PlaceOrder) o;
        return Double.compare(that.getTotalCost(), getTotalCost()) == 0 && getZipcode() == that.getZipcode() && Objects.equals(getId(), that.getId()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getOrderStatus(), that.getOrderStatus()) && Objects.equals(getOrderDate(), that.getOrderDate()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getState(), that.getState()) && Objects.equals(getCountry(), that.getCountry()) && Objects.equals(getPhonenumber(), that.getPhonenumber()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getCognome(), that.getCognome()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getUseremail(), that.getUseremail()) && Objects.equals(getCreditCard(), that.getCreditCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getOrderStatus(), getOrderDate(), getTotalCost(), getAddress(), getCity(), getState(), getCountry(), getZipcode(), getPhonenumber(), getNome(), getCognome(), getDescription(), getUseremail(), getCreditCard());
    }

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate=" + orderDate +
                ", totalCost=" + totalCost +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipcode=" + zipcode +
                ", phonenumber='" + phonenumber + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", description='" + description + '\'' +
                ", useremail='" + useremail + '\'' +
                ", creditCard=" + creditCard +
                '}';
    }
}
