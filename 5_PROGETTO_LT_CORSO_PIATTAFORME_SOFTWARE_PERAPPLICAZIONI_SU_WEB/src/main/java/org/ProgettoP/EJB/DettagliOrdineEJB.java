package org.ProgettoP.EJB;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ProgettoP.model.dettaglioOrdine;
import org.ProgettoP.model.prodotto;



@Stateful(mappedName = "DettagliOrdineEJB")
public class DettagliOrdineEJB {
	
	@PersistenceContext
	EntityManager em;
	
	
	
	public dettaglioOrdine inserisciOrdine(long id,String user) {
		 System.out.println("Utente----------->"+user+"\nha aggiunto prodotto-------->"+id);
		 Query q1 = em.createQuery("select u.id from utente u where u.username='"+user+"'");
		 System.out.println("q1 result\n"+q1);
		 long n1 = (long) q1.getSingleResult();
		 Query q2 = em.createQuery("select p from prodotto p where p.id='"+id+"'");
		 System.out.println("q2 result\n"+q2);
		 
		 prodotto n2 = (prodotto) q2.getSingleResult();
		 dettaglioOrdine so = new dettaglioOrdine();
		 if (this.finder(id,n1)== null) {
		    so.setPrezzo(n2.getPrezzo());
		    so.setProdottoID((int)n2.getId());
	        so.setQuantità(1);
	        so.setUtenteId(n1);
		    em.persist(so);
	    	return so;
	    	}
		  
		  Query q = em.createQuery("select so from dettaglioOrdine so where so.prodottoID ='"+id+"'");
		  System.out.println(q);
			 
		  so = (dettaglioOrdine) q.getSingleResult();
		  so.setUtenteId(n1);
		  so.setPrezzo(n2.getPrezzo());
		  so.setProdottoID(n2.getId());
		  if(n2.getQuantità()>so.getQuantità())so.setQuantità(so.getQuantità()+1);
		  em.merge(so);
		  return so;
	    }
	 
	 public dettaglioOrdine finder(long id, long n1) {
	    	
	        Query q = em.createQuery("select d from dettaglioOrdine d where d.prodottoID ='"+id+"'"+" and d.UtenteId='"+n1+"'");
	        System.out.println("q3 result\n"+q);
			 
	        try{ return (dettaglioOrdine)q.getSingleResult();}
	        catch(Exception e) {System.out.println("Errore");return null;}
	        
	    }
	 
	 public void rimuovi( long id, long idUser) {
		 
		 Query q = em.createQuery("select d from dettaglioOrdine d where d.prodottoID ='"+id+"'"+" and d.UtenteId='"+idUser+"'");
		 try {
			 dettaglioOrdine d = (dettaglioOrdine) q.getSingleResult();
		     System.out.println(d.toString());
			 if(d.getQuantità()-1>0) {
			     d.setQuantità(d.getQuantità()-1);
			     System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+d.getQuantità());
			     aggiornaDettaglioOrdine(d);
			 }
			 else em.remove(d);
		 }catch(Exception e) {
			 FacesContext.getCurrentInstance().addMessage("",new FacesMessage("l'Ordine non può essere eliminato"));
		 }
	 }
	 public dettaglioOrdine aggiornaDettaglioOrdine(dettaglioOrdine d) {
	    	em.merge(d);
	    	return d;
	    }

	public double calcolaTOT(Collection<dettaglioOrdine> d) {
		double somma=0;
    	for(dettaglioOrdine x:d) {
    		somma+=x.getPrezzo()*x.getQuantità();
    	}
    	return somma;
	}
	 
}