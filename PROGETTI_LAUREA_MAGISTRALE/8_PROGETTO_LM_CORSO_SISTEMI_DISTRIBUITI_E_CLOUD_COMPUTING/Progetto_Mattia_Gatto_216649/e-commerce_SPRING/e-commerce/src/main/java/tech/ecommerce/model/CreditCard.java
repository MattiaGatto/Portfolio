package tech.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "creditcard")
public class CreditCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailcard;

    private String nomecard,cognomecard;
    private String creditnumber;
    private String creditcode,expiration_date;

    private double credit;



    public CreditCard() {

    }

    public CreditCard(String emailcard, String nomecard, String cognomecard, String creditnumber, String creditcode, String expiration_date, double credit) {
        this.emailcard = emailcard;
        this.nomecard = nomecard;
        this.cognomecard = cognomecard;
        this.creditnumber = creditnumber;
        this.creditcode = creditcode;
        this.expiration_date = expiration_date;
        this.credit = credit;
    }

    public String getEmailcard() {
        return emailcard;
    }

    public void setEmailcard(String emailcard) {
        this.emailcard = emailcard;
    }

    public String getNomecard() {
        return nomecard;
    }

    public void setNomecard(String nomecard) {
        this.nomecard = nomecard;
    }

    public String getCognomecard() {
        return cognomecard;
    }

    public void setCognomecard(String cognomecard) {
        this.cognomecard = cognomecard;
    }

    public String getCreditnumber() {
        return creditnumber;
    }

    public void setCreditnumber(String creditnumber) {
        this.creditnumber = creditnumber;
    }

    public String getCreditcode() {
        return creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return Double.compare(that.credit, credit) == 0 && Objects.equals(getId(), that.getId()) && Objects.equals(emailcard, that.emailcard) && Objects.equals(nomecard, that.nomecard) && Objects.equals(cognomecard, that.cognomecard) && Objects.equals(creditnumber, that.creditnumber) && Objects.equals(creditcode, that.creditcode) && Objects.equals(expiration_date, that.expiration_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), emailcard, nomecard, cognomecard, creditnumber, creditcode, expiration_date, credit);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", email='" + emailcard + '\'' +
                ", nome='" + nomecard + '\'' +
                ", cognome='" + cognomecard + '\'' +
                ", creditnumber='" + creditnumber + '\'' +
                ", creditcode='" + creditcode + '\'' +
                ", expiration_date='" + expiration_date + '\'' +
                ", credit=" + credit +
                '}';
    }
}
