package EAV;

public interface Espressione {
	Espressione getRight();
	Espressione getLeft();
	void setRight(Espressione e);
	void setLeft(Espressione e);
	int interpreta(String contesto);
}//Espressione
