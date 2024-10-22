package org.ProgettoP.model;

import javax.persistence.*;


import java.io.Serializable;


@Entity
@Table(name = "DettaglioOrdine")
public class dettaglioOrdine implements Serializable {

	private static final long serialVersionUID = 1L;

	public dettaglioOrdine() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private long prodottoID;
	
	
	private long UtenteId;

	private int quantità;
	private double prezzo;
	public long getUtenteId() {
		return UtenteId;
	}
	
	public void setUtenteId(long utenteId) {
		UtenteId = utenteId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public long getProdottoID() {
		return prodottoID;
	}

	public void setProdottoID(long prodottoID) {
		this.prodottoID = prodottoID;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
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
		dettaglioOrdine other = (dettaglioOrdine) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
    public String toString() {
        return "{" + "DettaglioOrdine=" + id + ", prodotto=" + prodottoID + ", quanità=" + quantità +", prezzo=" + prezzo +"}\n";
    }

}