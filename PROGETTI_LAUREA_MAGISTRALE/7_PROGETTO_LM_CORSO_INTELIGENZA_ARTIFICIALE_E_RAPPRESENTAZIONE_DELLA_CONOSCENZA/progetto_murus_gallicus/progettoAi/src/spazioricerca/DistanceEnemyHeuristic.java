package spazioricerca;

public class DistanceEnemyHeuristic extends Heuristic{
    public DistanceEnemyHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n, int colore) {
        byte[]stato = n.getStato();
        int ultimo_pezzo=-1;
        int primo_pezzo=-1;
        int enemy_color = (colore==0)?1:0;
        int riga = 0;
        int base = (enemy_color==0)?0:6;
        for (int i = 0;i<56; i++){
            if(stato[i]==2*enemy_color+2||stato[i]==2*enemy_color+1){
                if(primo_pezzo==-1){
                    primo_pezzo = i;
                }
                ultimo_pezzo=i;
            }
        }
        riga=(colore==0)?ultimo_pezzo/8:primo_pezzo/8;
        return ((float) Math.abs(base - riga))/((float)6);
    }
}
