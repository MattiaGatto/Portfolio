package EAV;
public class Variabile extends Fattore {
	
	private String identificatore;
	private int valore;
	
	public Variabile(String identificatore, int valore) {
		this.identificatore = identificatore;
		this.valore = valore;
	}
	
	public String getId() {
		return identificatore;
	}
	
	public int getValore() {
		return valore;
	}

	@Override
	public Espressione getLeft() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Espressione getRight() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLeft(Espressione e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRight(Espressione e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return this.identificatore;
	}

	@Override
	public int interpreta(String contesto) {
		return valore;
	}

}//FattoreVariabile