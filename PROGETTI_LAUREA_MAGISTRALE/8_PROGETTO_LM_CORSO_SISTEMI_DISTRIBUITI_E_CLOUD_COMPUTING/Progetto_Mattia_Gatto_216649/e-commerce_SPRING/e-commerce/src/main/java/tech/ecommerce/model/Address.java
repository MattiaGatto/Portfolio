package tech.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String city;
    private String state;
    private String country;
    private int zipcode;
    private String phonenumber;
    private String email;
    private String nome,cognome;

    public Address(String address, String city, String state, String country, int zipcode, String phonenumber, String email, String nome, String cognome) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.phonenumber = phonenumber;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Address() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address1 = (Address) o;
        return getZipcode() == address1.getZipcode() && Objects.equals(getId(), address1.getId()) && Objects.equals(getAddress(), address1.getAddress()) && Objects.equals(getCity(), address1.getCity()) && Objects.equals(getState(), address1.getState()) && Objects.equals(getCountry(), address1.getCountry()) && Objects.equals(getPhonenumber(), address1.getPhonenumber()) && Objects.equals(getEmail(), address1.getEmail()) && Objects.equals(getNome(), address1.getNome()) && Objects.equals(getCognome(), address1.getCognome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getCity(), getState(), getCountry(), getZipcode(), getPhonenumber(), getEmail(), getNome(), getCognome());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipcode=" + zipcode +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }
}
