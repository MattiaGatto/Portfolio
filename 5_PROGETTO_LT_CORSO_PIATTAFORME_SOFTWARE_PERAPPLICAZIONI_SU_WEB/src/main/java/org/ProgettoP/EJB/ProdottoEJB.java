package org.ProgettoP.EJB;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ProgettoP.model.prodotto;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Stateless
public class ProdottoEJB {
    
    @PersistenceContext
	EntityManager em;
    
    public ProdottoEJB() {}
	 
    public prodotto inserisciProdotto(String nome, double prezzo,int qta) {
    	System.out.println("------------------------------------------------------------\n"+nome+"     "+prezzo+"     "+qta);
    	Query q = em.createQuery("SELECT p FROM prodotto p WHERE p.nome ='"+nome+"' and p.prezzo ='"+prezzo+"'");
    	System.out.println("\n-----------------------------------------------------------------------------------------------");
    	try {
    		prodotto p = (prodotto)q.getSingleResult();
        	p.setQuantità(p.getQuantità()+qta);
    		System.out.println("Aggiorno il prodotto------->"+q.getSingleResult()+"con quantità"+(p.getQuantità()));
    		return aggiornaProdotto(p);
    	}catch(Exception e){
        	System.out.println("Il prodotto va creato-----------------------------------------------------------------------------------------------");
        	prodotto p = new prodotto();
        	p.setQuantità(qta);
        	p.setNome(nome);
        	p.setPrezzo(prezzo);
        	em.persist(p);
    		return p;
    	}
    }
    
    public void rimuovi( long id,int quantità) {// modificare rimuovondo anche tutti i singoli ordini
		 Query q = em.createQuery("select p from prodotto p where p.id ='"+id+"'");
		 try {
			 System.out.println("------------------------------");
			 prodotto p = (prodotto) q.getSingleResult();
			 System.out.println(p.toString());
			 if(p.getQuantità()-quantità>0) {
			     p.setQuantità(p.getQuantità()-quantità);
			     aggiornaProdotto(p);
			 }
			 else em.remove(p);
		 }catch(Exception e) {
			 FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Id prodotto inserito sbagliato"));
		 }
	 }

    
    public List<prodotto> trovaProdotti() {
		Query q = em.createQuery("select p from prodotto p");
		return q.getResultList();
    }
    
    public prodotto find(long pid) {
		prodotto p = em.find(prodotto.class, pid);
    	return p;
	}

    public prodotto aggiornaProdotto(prodotto p) {
    	em.merge(p);
    	return p;
    }
}
