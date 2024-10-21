package org.ProgettoP.model;

import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "utente")
@NamedQuery(
	    name="findAllUtenti",
	    query="SELECT OBJECT(u) FROM utente u WHERE u.username = :username and u.password = :password"
	)
@XmlRootElement
@DiscriminatorValue("utente")

public class utente implements Serializable {

	private static final long serialVersionUID = 1L;

	public utente() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	private String username;
	private String password;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		utente other = (utente) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
    public String toString() {
        return super.toString()+" e utente{" + "Codice Utente=" + id + '}';
    }

}