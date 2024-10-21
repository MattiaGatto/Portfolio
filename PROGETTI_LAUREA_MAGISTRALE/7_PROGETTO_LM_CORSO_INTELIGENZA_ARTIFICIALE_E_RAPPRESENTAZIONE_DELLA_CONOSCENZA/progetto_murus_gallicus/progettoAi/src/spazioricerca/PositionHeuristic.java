package spazioricerca;

public class PositionHeuristic extends Heuristic{

    public PositionHeuristic(float peso){ super(peso);}

    public float calcola(Nodo n, int colore) {
        float torri_nostre = 0;
        byte[]stato = n.getStato();
        int offset = (colore == 0)? 16:32;
        for(int i = 0;i < 8;i++){
            if(stato[i+offset] == 2*colore+2){
                torri_nostre++;
            }
        }
        return torri_nostre / (float) 8;
    }
}
