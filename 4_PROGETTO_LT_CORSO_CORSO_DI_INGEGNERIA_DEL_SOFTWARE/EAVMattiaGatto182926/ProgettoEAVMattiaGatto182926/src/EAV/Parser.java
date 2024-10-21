package EAV;

import EAV.Analizzatore.Simbolo;

public class Parser{
	
	private Simbolo simbolo;
	private Analizzatore lettore;
	private Espressione x = null;
	private Contesto c = new Contesto();
	
	public Parser(String expr, Contesto c) {
		lettore = new Analizzatore(expr);
		this.c = c;
		espressione();
	}

	public void espressione() {
		termine();
		while(simbolo == Analizzatore.Simbolo.PIU ||simbolo == Analizzatore.Simbolo.MENO) {
			Termine t = (Termine) assegna();
			t.setLeft(x);
			termine();
			t.setRight(x);
			x = t;
		}
	}

	public void termine() {
		fattore();
		while(simbolo == Analizzatore.Simbolo.PER || simbolo == Analizzatore.Simbolo.DIVISO) {
			Fattore f = (Fattore) assegna();
			f.setLeft(x);
			fattore();
			f.setRight(x);
			x = f;
		}
	}

	public void fattore() {
		simbolo = lettore.prossimoSimbolo();
		if(simbolo == Analizzatore.Simbolo.COSTANTE) {
			x = new Costante(simbolo.getCostante());
			simbolo = lettore.prossimoSimbolo();
		}
		else if(simbolo == Analizzatore.Simbolo.VARIABILE) {
			x = new Variabile(simbolo.getVariabile(),c.getContesto().get(simbolo.getVariabile()));
			simbolo = lettore.prossimoSimbolo();
		}
		else if(simbolo == Analizzatore.Simbolo.TONDA_A) {
			espressione();
			atteso(Analizzatore.Simbolo.TONDA_C);
		}
		else {
			throw new RuntimeException("Valore in ingresso inatteso!");
		}
	}
	
	 private Espressione assegna() {
	        switch(simbolo) {
	            case PIU:
	                return new Addizione();
	            case MENO:
	                return new Sottrazione();
	            case PER:
	                return new Moltiplicazione();
	            case DIVISO:
	                return new Divisione();
	            default: return null;
	       }
	 }

	
	public Espressione getE() {
		return x;
	}
	
	private void atteso(Simbolo s) {
		if(simbolo != s) {
			throw new RuntimeException();
		}
		simbolo = lettore.prossimoSimbolo();
	}
	
}//Parser