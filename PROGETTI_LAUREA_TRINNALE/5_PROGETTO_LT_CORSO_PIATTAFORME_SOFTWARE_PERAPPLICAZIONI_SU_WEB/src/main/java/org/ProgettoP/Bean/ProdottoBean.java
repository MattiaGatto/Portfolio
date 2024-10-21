package org.ProgettoP.Bean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import java.io.Serializable;
import java.util.List;

import org.ProgettoP.EJB.ProdottoEJB;
import org.ProgettoP.model.prodotto;


@ManagedBean
@SessionScoped

public class ProdottoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private prodotto p;
	
	public prodotto getProdotto() {
		return p;
	}

	public void setProdotto(prodotto p) {
		this.p = p;
	}
	@EJB
	private ProdottoEJB ejbProdotto;
	
	public List<prodotto> lista(){
		return ejbProdotto.trovaProdotti();
	}
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void inserisci(String nome, double prezzo,int qta) {
		ejbProdotto.inserisciProdotto(nome, prezzo, qta);
	}
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void rimuoviProdotto( long id,int quantità) {
		ejbProdotto.rimuovi(id,quantità);
	}
    
    @PostConstruct
	public void init(){
	p=new prodotto();
	}
	
}