package EAV;

public class Costante extends Fattore{

	private int c;
	
	public Costante(int c) {
		this.c = c;
	}
	
	public int getValore() {
		return c;
	}
	
	@Override
	public Espressione getLeft() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Espressione getRight() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return Integer.toString(c);
	}

	@Override
	public void setLeft(Espressione e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRight(Espressione e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int interpreta(String contesto) {
		return c;
	}

}//FattoreCostante