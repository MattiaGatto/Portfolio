package spazioricerca;

public class DistanceHeuristic extends Heuristic{

    public DistanceHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n, int colore) {
        byte[]stato = n.getStato();
        int ultimo_pezzo=-1;
        int primo_pezzo=-1;
        int riga = 0;
        int base = (colore==0)?0:6;
        for (int i = 0;i<56; i++){
            if(stato[i]==2*colore+2||stato[i]==2*colore+1){
                if(primo_pezzo==-1){
                    primo_pezzo = i;
                }
                ultimo_pezzo=i;
            }
        }
        riga=(colore==0)?primo_pezzo/8:ultimo_pezzo/8;
        return (1-((float)Math.abs(base - riga)/(float)6));
    }
}
