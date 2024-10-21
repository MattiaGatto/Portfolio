package spazioricerca;

public abstract class Heuristic {
    protected float peso;
    public Heuristic(float peso){
        this.peso = peso;
    }
    public abstract float calcola(Nodo n, int colore);
    public float getPeso(){
        return peso;
    }
}
