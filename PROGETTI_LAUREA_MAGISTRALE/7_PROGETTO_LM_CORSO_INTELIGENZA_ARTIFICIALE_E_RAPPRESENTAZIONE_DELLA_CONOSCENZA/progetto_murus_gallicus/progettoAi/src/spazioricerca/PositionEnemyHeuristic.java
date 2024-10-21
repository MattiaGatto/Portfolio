package spazioricerca;

public class PositionEnemyHeuristic extends Heuristic {

    public PositionEnemyHeuristic(float peso){ super(peso);}

    public float calcola(Nodo n, int colore) {
        int enemyColor = (colore == 0)? 1 : 0;
        float torri = 0;
        byte[]stato = n.getStato();
        int offset = (enemyColor == 0)? 16:32;
        for(int i = 0;i < 8;i++){
            if(stato[i+offset] == 2*enemyColor+2){
                torri++;
            }
        }
        return torri / (float) 8;
    }
}
