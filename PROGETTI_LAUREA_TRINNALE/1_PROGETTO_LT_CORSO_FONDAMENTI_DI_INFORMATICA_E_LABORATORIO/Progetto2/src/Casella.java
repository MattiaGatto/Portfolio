public class Casella {
	private int valore;
	private int riga;
	private int colonna;
	private boolean scoperta=false;
	

	public Casella(int v){
		valore=v;
	}
	
	public int getRiga() {
		return riga;
	}

	public int getColonna() {
		return colonna;
	}

	public int getValore(){
	    return valore;
	}
	public boolean isScoperta() {
		return scoperta;
	}
	public void setScoperta(boolean scoperta) {
		this.scoperta = scoperta;
	}
	@Override
	public String toString() {
		return "Casella [valore=" + valore + ", scoperta=" + scoperta + "]";
	}
	
}