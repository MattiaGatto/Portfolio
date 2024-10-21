package spazioricerca;

public class DefeatHeuristic extends Heuristic {
    public DefeatHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n, int colore) {
        byte[] scacchiera = n.getStato();
        int enemy_color = (colore == 0)? 1:0;
        int offset = 48;
        for(int i = 0; i < 8;i++){
            if(scacchiera[offset*enemy_color+i] == enemy_color*2+1){
                return -50;
            }
        }
        int num_torri = 0;
        for(int i = 0; i < 56;i++){
            if (scacchiera[i] == 2*colore+2){
                num_torri++;
            }
        }
        if(num_torri == 0){
            return -50;
        }
        return 0;
    }
}
