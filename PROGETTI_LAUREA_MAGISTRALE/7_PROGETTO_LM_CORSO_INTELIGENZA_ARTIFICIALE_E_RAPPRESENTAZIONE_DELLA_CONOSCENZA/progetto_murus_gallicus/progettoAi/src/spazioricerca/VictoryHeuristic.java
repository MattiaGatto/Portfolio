package spazioricerca;

public class VictoryHeuristic extends Heuristic {
    public VictoryHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n, int colore) {
        byte[] scacchiera = n.getStato();
        int enemy_color = (colore == 0)? 1:0;
        int offset = 48;
        for(int i = 0; i < 8;i++){
            if(scacchiera[offset*colore+i] == colore*2+1){
                return +50;
            }
        }
        int num_torri_avversarie = 0;
        for(int i = 0; i < 56;i++){
            if (scacchiera[i] == 2*enemy_color+2){
                num_torri_avversarie++;
            }
        }
        if(num_torri_avversarie == 0){
            return +50;
        }
        return 0;
    }
}
