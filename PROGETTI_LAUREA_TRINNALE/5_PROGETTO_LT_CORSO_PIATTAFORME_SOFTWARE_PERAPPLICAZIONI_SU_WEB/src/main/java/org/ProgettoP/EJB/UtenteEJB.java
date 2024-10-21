package org.ProgettoP.EJB;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ProgettoP.model.utente;


@Stateless
public class UtenteEJB {
	
	@PersistenceContext
	EntityManager em;
	
	
public UtenteEJB() {};

public void create(utente enitity) {
	em.persist(enitity);
}

public String check(String username,String password) {
	if(username.equals("")||password.equals(""))return null;
	Query q = em.createQuery("SELECT password FROM utente WHERE username ='"+username+"' and password ='"+password+"'");
	System.out.println(q.toString());
	try{ 
		return (String)q.getSingleResult();
		}
	catch(Exception e) {
	    FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Utente gia registrato o password errata"));
	    return null;
		}
  }


public long getIdUser(String username,String password) {
	  Query q = em.createQuery("SELECT id FROM utente WHERE username ='"+username+"' and password ='"+password+"'");
	  try{ 
		  return (long)q.getSingleResult();
	  }catch(Exception e) {
		  System.out.println("Errore");
		  e.printStackTrace();
		  return -1;
		  }
	  }

public void aggiungi(utente n) {
	em.persist(n);
}




}
