package spazioricerca;

public class EnemyNmHeuristic extends Heuristic{
    public EnemyNmHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n, int colore) {
        float torri_avversario = 0;
        float totali_avversario = 0;
        int enemy_color = (colore==0)?1:0;
        byte[]stato = n.getStato();
        for(int i = 0; i<56; i++){
            if(stato[i]==2*enemy_color+2){
                torri_avversario++;
                totali_avversario++;
            }
            if(stato[i]==2*enemy_color+1){
                totali_avversario++;
            }
        }
        return 1-(torri_avversario/totali_avversario);
    }
}
