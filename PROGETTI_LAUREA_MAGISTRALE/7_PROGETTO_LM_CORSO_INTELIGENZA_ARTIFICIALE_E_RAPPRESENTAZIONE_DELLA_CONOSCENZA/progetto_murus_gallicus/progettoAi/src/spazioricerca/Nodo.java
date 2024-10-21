package spazioricerca;

public class Nodo {
    private int color;
    private byte[]stato;
    private Nodo predecessore;
    private byte[]mossa;
    private float hval=-100;
    private int livello;

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public byte[] getStato() {
        return stato;
    }

    public void setStato(byte[] stato) {
        this.stato = stato;
    }

    public Nodo getPredecessore() {
        return predecessore;
    }

    public void setPredecessore(Nodo predecessore) {
        this.predecessore = predecessore;
    }

    public byte[] getMossa() {
        return mossa;
    }

    public void setMossa(byte[] mossa) {
        this.mossa = mossa;
    }

    public float getHval() {
        return hval;
    }

    public void setHval(float hval) {
        this.hval = hval;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }
}
