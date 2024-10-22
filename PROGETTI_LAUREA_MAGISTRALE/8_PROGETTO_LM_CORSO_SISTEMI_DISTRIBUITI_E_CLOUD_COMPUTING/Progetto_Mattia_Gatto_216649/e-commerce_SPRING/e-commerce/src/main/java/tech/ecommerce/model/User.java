package tech.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String username;
    private int age;
    private String nome,cognome,email,jobTitle,phone,imageUrl,password,usertype;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "addressid")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "carrelloid")
    private Carrello carrello;

    public User() {}

    public User( int age, String nome,String cognome, String email, String jobTitle, String phone, String imageUrl, String password,String usertype, Address address, String username,Carrello carrello ){
        this.age = age;
        this.nome = nome;
        this.cognome=cognome;
        this.email = email;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.password = password;
        this.usertype=usertype;
        this.address = address;
        this.username = username;
        this.carrello=carrello;
    }


    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {this.age = age;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getAge() == user.getAge() && Objects.equals(getId(), user.getId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getNome(), user.getNome()) && Objects.equals(getCognome(), user.getCognome()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getJobTitle(), user.getJobTitle()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getImageUrl(), user.getImageUrl()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getUsertype(), user.getUsertype()) && Objects.equals(getAddress(), user.getAddress()) && Objects.equals(getCarrello(), user.getCarrello());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getAge(), getNome(), getCognome(), getEmail(), getJobTitle(), getPhone(), getImageUrl(), getPassword(), getUsertype(), getAddress(), getCarrello());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", phone='" + phone + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", password='" + password + '\'' +
                ", usertype='" + usertype + '\'' +
                ", address=" + address +
                ", carrello=" + carrello +
                '}';
    }

}
