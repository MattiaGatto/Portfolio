package EAV;

import java.util.StringTokenizer;


public class Analizzatore {
	
	private String espressione;
	private StringTokenizer st;
	
	public enum Simbolo {
		PIU,MENO,PER,DIVISO,COSTANTE,VARIABILE,TONDA_A,TONDA_C,FINE;
		
		private String variabile;
		private int costante;
		
		Simbolo() {
			variabile = null;
			costante = 0;
		}
		
		public String getVariabile() {
			return variabile;
		}
		
		public void setVariabile(String s) {
			this.variabile = s;
		}
		
		public int getCostante() {
			return costante;
		}
		
		public void setCostante(int n) {
			this.costante = n;
		}
		
	}//simbolo
	
	
	public Analizzatore(String espressione) {
		this.espressione = espressione;
		st = new StringTokenizer(this.espressione,"()+-*/",true);
	}
	
	public Simbolo prossimoSimbolo() {
		String intero = "[\\d]+";
		String lettera = "[(a-z)|(A-Z)]+";
		String identificatore = lettera+"([\\d]*|[(a-z)|(A-Z]*)";
		if(!st.hasMoreTokens()) {
			return Simbolo.FINE;
		}
		String s = st.nextToken();
		if(s.equals("+")) { return Simbolo.PIU; }
		if(s.equals("-")) { return Simbolo.MENO; }
		if(s.equals("*")) { return Simbolo.PER; }
		if(s.equals("/")) { return Simbolo.DIVISO; }
		if(s.equals("(")) { return Simbolo.TONDA_A; }
		if(s.equals(")")) { return Simbolo.TONDA_C; }
		if(s.matches(intero)) {
				int c = Integer.parseInt(s);
				Simbolo simbolo = Simbolo.COSTANTE;
				simbolo.setCostante(c);
				return simbolo;
		}
		if(s.matches(identificatore)) {
			Simbolo simbolo = Simbolo.VARIABILE;
			simbolo.setVariabile(s);
			return simbolo;
		}
		return Simbolo.FINE;
	}
	
	
    
}//AnalizzatoreLessicale