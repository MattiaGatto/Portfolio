package EAV;
public class Sottrazione extends addop {
	
	private Espressione left;
	private Espressione right;

	@Override
	public Espressione getLeft() {
		return left;
	}

	@Override
	public Espressione getRight() {
		return right;
	}

	@Override
	public void setLeft(Espressione e) {
		this.left = e;
	}

	@Override
	public void setRight(Espressione e) {
		this.right = e;
	}
	
	@Override
	public String toString() {
		return this.getLeft().toString()+"-"+this.getRight().toString();
	}

	@Override
	public int interpreta(String contesto) {
		return this.getLeft().interpreta(contesto)-this.getRight().interpreta(contesto);
	}

}//Min