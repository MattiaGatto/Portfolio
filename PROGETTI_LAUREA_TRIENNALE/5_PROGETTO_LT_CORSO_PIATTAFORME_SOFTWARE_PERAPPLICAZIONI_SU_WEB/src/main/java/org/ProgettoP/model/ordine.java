package org.ProgettoP.model;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.criteria.Order;

import java.util.*;


@Entity
@Table(name = "Ordine")
@NamedQueries({
	@NamedQuery(name = "findAllOrders", query = "select o from ordine o")
	})
public class ordine implements Serializable {

	private static final long serialVersionUID = 1L;

	public ordine() {
	}
	
	private long utenteID;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(cascade = {CascadeType.PERSIST})
	private Collection<dettaglioOrdine> dettaglioOrdine = new ArrayList<dettaglioOrdine>(50);
	
	private Date data;
	private double prezzo;
	private String ListaprodottiOrdine;
	public String getListaprodottiOrdine() {
		return ListaprodottiOrdine;
	}

	public void setListaprodottiOrdine(String listaprodottiOrdine) {
		ListaprodottiOrdine = listaprodottiOrdine;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<dettaglioOrdine> getDettaglioOrdine() {
	    return dettaglioOrdine;
	}

	public void setDettaglioOrdine(Collection<dettaglioOrdine> param) {
	    this.dettaglioOrdine = param;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		ordine other = (ordine) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

	public long getUtenteID() {
		return utenteID;
	}

	public void setUtenteID(long utenteID) {
		this.utenteID = utenteID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Ordine{" + "cliente=" + utenteID + ", id=" + id + '}';
	}

}