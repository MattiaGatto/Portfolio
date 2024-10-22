package org.ProgettoP.Bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.ProgettoP.EJB.OrdineEJB;
import org.ProgettoP.EJB.UtenteEJB;
import org.ProgettoP.model.utente;

@ManagedBean
@SessionScoped
public class UtenteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private utente utente;
	
	
	
	public utente getUtente() {
		return utente;
	}

	public void utente (utente utente) {
		this.utente = utente;
	}
	@EJB
	private UtenteEJB ejbUtente;
	
	@EJB
	private OrdineEJB ejbOrdine;
	
	public String controllo(String u ,String p ) {
		if (ejbUtente.check(u, p) == null) {
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Dati inseriti sbagliati"));	
    		return null;
		}
		if(u.equals("Admin")) return "homeAdmin?faces-redirect=true";
		System.out.println("OK");
		return "home?faces-redirect=true";
	}
	public String registra(String u ,String p ,String p2) {
		if(u.equals("")||p.equals("")||p2.equals("")) {
			FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Dati non completi"));
			return null;
		}
		if (ejbUtente.check(u, p) == null) {
			if(!p.equals(p2)) {
				FacesContext.getCurrentInstance().addMessage("",new FacesMessage("La pasword non corrisponde"));
				return null;
			}
			else {
				utente N= new utente();
				N.setPassword(p);
				N.setUsername(u);
				ejbUtente.aggiungi(N);
				return "login?faces-redirect=true";
			}
		}
		FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Dati inseriti corrispondenti ad un utente già registrato"));
		System.out.println("OK");
		return null;
	}
	
	

	@PostConstruct
	public void init(){
	utente=new utente();
	}

 public void Stampa (String a) {
	 System.out.println(a);
 }
	
 public long getIdeno(String u ,String p ) {
		long i=ejbUtente.getIdUser(u, p);
		if (i == -1)return -1;
		System.out.println("Utente restituito dal metodo getIdeno------------id>"+i);
		return i;
		}
}