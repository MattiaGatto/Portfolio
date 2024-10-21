package org.ProgettoP.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ProgettoP.model.dettaglioOrdine;
import org.ProgettoP.model.ordine;



@Stateful(mappedName = "OrdineEJB")
@LocalBean
public class OrdineEJB {
	
	@PersistenceContext
	EntityManager em;
	
	public OrdineEJB() {
	    }
	 
	public ordine inserisciOrdine(ordine o) {
    	em.persist(o);
    	return o;
    }
	
	 public void rimuoviOrdine(ordine o) {
    	em.remove(o);
    }

	public List<dettaglioOrdine> trovaLista(String user) {
		Query q = em.createQuery("select d from dettaglioOrdine as d, utente as u where d.UtenteId = u.id  and u.username='"+user+"' ");
		return q.getResultList();
	 }
		
	public dettaglioOrdine inserisciDettaglioOrdine(dettaglioOrdine d) {
	   	em.persist(d);
	   	return d;
	}
	public dettaglioOrdine inserisciSingoloOrdine(dettaglioOrdine d) {
    	em.merge(d);
    	return d;
	}

	public List<ordine> trovaListaOrdini(String user) {
		Query q = em.createQuery("select o from ordine as o, utente as u where o.utenteID = u.id  and u.username='"+user+"' ");
		return q.getResultList();
	}

	
}