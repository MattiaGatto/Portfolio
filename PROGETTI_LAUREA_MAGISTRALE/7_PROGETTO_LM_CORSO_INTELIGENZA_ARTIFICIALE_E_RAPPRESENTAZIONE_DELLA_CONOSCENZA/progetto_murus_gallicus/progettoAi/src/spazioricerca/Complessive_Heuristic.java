package spazioricerca;

public class Complessive_Heuristic extends NewHeuristic{
    public Complessive_Heuristic(float peso1,float peso2, float peso3,float peso4,float peso5,float peso6,float peso7) {
            super(peso1,peso2,peso3,peso4,peso5,peso6,peso7);
    }

    private float BalancedHeuristic;
    private float ControlCenterHeuristic;
    private float DistanceHeuristic;
    private float DistanceEnemyHeuristic;
    private float EnemyNmHeuristic;
    private float VictoryHeuristic;
    private float DefeatHeuristic;

    @Override
    public float calcola(Nodo n, int colore) {
        byte[]stato = n.getStato();
        int coloreAmico = colore;
        float num_torri = 0;
        float num_pedine = 0;
        float totale = 0;
        int enemy_color=(colore==0)?1:0;
        float torri_avversarie_centro=0;
        float torri_nostre_centro = 0;
        int ultimo_pezzo_nostro=-1;
        int primo_pezzo_nostro=-1;
        int riga_nostra = 0;
        int base_nostra = (colore==0)?0:6;
        int ultimo_pezzo_avversario=-1;
        int primo_pezzo_avversario=-1;
        int riga_avversaria = 0;
        int base_avversario = (enemy_color==0)?0:6;
        float torri_avversario = 0;
        float totali_avversario = 0;
        int ris=0;
        int ris_def=0;
        for(int i = 0; i<56; i++){
            //BalanceHeuristic
            if (stato[i] == coloreAmico * 2 + 2) {
                totale++;
                num_torri++;
            }
            if (stato[i] == coloreAmico * 2 + 1) {
                totale++;
                num_pedine++;
            }
            //ControlCenterHeuristic
            if((i>=18&&i<=21)||(i>=26&&i<=29)||(i>=34&&i<=37)) {
                int var = stato[i];
                if (var == (colore * 2) + 2) {
                    torri_nostre_centro++;
                } else if (var == (enemy_color * 2) + 2) {
                    torri_avversarie_centro++;
                }
            }
            //DistanceHeuristic
            if(stato[i]==2*colore+2||stato[i]==2*colore+1){
                if(primo_pezzo_nostro==-1){
                    primo_pezzo_nostro = i;
                }
                ultimo_pezzo_nostro=i;
            }
            //DistanceEnemyHeuristic
            if(stato[i]==2*enemy_color+2||stato[i]==2*enemy_color+1){
                if(primo_pezzo_avversario==-1){
                    primo_pezzo_avversario = i;
                }
                ultimo_pezzo_avversario=i;
            }
            //EnemyNmHeuristic
            if(stato[i]==2*enemy_color+2){
                torri_avversario++;
                totali_avversario++;
            }
            if(stato[i]==2*enemy_color+1){
                totali_avversario++;
            }
            //defeat e victory
            if(i>=0&&i<=7) {
                if ((stato[i] == enemy_color * 2 + 1)&&enemy_color==0) {
                    ris = -50;
                }
                if ((stato[i] == colore * 2 + 1)&&colore==0) {
                    ris_def = +50;
                }
            }
            if(i>=48&&i<=55) {
                if ((stato[i] == enemy_color * 2 + 1)&&enemy_color==1) {
                    ris = -50;
                }
                if ((stato[i] == colore * 2 + 1)&&colore==1) {
                    ris_def = +50;
                }
            }
        }
        BalancedHeuristic=PesoBalancedHeuristic*(1 - (Math.abs((num_torri - num_pedine))/ totale));
        ControlCenterHeuristic=PesoControlCenterHeuristic*((torri_nostre_centro-torri_avversarie_centro)/(torri_nostre_centro+torri_avversarie_centro+1));
        riga_nostra=(colore==0)?primo_pezzo_nostro/8:ultimo_pezzo_nostro/8;
        DistanceHeuristic=PesoDistanceHeuristic*(1-((float)Math.abs(base_nostra - riga_nostra)/(float)6));
        riga_avversaria=(colore==0)?ultimo_pezzo_avversario/8:primo_pezzo_avversario/8;
        DistanceEnemyHeuristic=PesoDistanceEnemyHeuristic*((float) Math.abs(base_avversario - riga_avversaria))/((float)6);
        EnemyNmHeuristic=PesoEnemyNmHeuristic*(1-(torri_avversario/totali_avversario));
        if(num_torri == 0){ris_def= -50;}
        if(torri_avversario == 0){return ris= +50;}
        VictoryHeuristic=PesoVictoryHeuristic*ris;
        DefeatHeuristic=PesoDefeatHeuristic*ris_def;
        return BalancedHeuristic+ControlCenterHeuristic+DistanceHeuristic+DistanceEnemyHeuristic+EnemyNmHeuristic+VictoryHeuristic+DefeatHeuristic;
    }


}
