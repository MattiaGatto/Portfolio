package org.ProgettoP.Bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.ProgettoP.EJB.DettagliOrdineEJB;
import org.ProgettoP.EJB.OrdineEJB;
import org.ProgettoP.EJB.ProdottoEJB;
import org.ProgettoP.model.dettaglioOrdine;
import org.ProgettoP.model.ordine;
import org.ProgettoP.model.prodotto;

@SessionScoped
@Named
public class OrdineBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	OrdineEJB oEJB;
	
	@EJB
	ProdottoEJB pEJB;

	@EJB
	DettagliOrdineEJB dEJB;
    
	public OrdineBean() {
       
    }
    public List<dettaglioOrdine> listaCarrello(String user){
		return oEJB.trovaLista(user);
	}
    public List<ordine> listaOrdini(String user){
		return oEJB.trovaListaOrdini(user);
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void aggiungiOrdine(long id, String user) {
    	System.out.println("utente------------>"+user);
    	
    	dEJB.inserisciOrdine(id,user);
    	
    }
    public void rimuoviOrdine(long id, long idUser) {
    	System.out.println("1");
    	System.out.print("Prodotto---------->"+id+"\nUtente----------->"+idUser+"\n");
    	dEJB.rimuovi(id,idUser);
    	System.out.println("2");
    }
	
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void completa(long id, Collection<dettaglioOrdine> d){
    	System.out.println(d.size());
    	if (d.size()==0)throw new RuntimeException();
    	ordine o = new ordine();
    	o.setUtenteID(id);
    	Date data= new Date(System.currentTimeMillis());
		o.setPrezzo(dEJB.calcolaTOT(d));
		o.setListaprodottiOrdine(d.toString());
		o.setData(data);
		oEJB.inserisciOrdine(o);
    	for(dettaglioOrdine l:d) {
    		prodotto p = pEJB.find(l.getProdottoID());
    		/*if(p==null) System.out.println("prodotto è nullo");
    		if (p.getPrezzo()!=l.getPrezzo()) {
    			throw new RuntimeException();
    		}*/
    		if(p.getQuantità()<l.getQuantità()) {
    			throw new RuntimeException();
    		}
    		o.getDettaglioOrdine().add(l);
    		oEJB.inserisciSingoloOrdine(l);
    		pEJB.rimuovi(p.getId(), l.getQuantità());
    		for(int i=0;i<l.getQuantità();++i)dEJB.rimuovi(p.getId(), id);
    	}
	}
    
    public String TotaleImporto(String user) {
    	return ""+dEJB.calcolaTOT(listaCarrello(user));
    	
    }
    
    public String confermaCompleta(long id, Collection<dettaglioOrdine> d) {
    	try {
    		this.completa(id, d);
    		FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Ordine Completato"));	
    		return "Ordine Completato";
    		}catch(Exception e) { FacesContext.getCurrentInstance().addMessage("",new FacesMessage("L'Ordine non può essere Completato"));return "Errore!";
    	    }
    	}
    

}