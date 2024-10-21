package spazioricerca;

import java.util.Arrays;
import java.util.List;

public class MinMaxAlphaBeta extends Ricerca {

    public boolean flag=false;
    public long time;

    public MinMaxAlphaBeta(int limit,List<Heuristic> h) { //, NewHeuristic) {
        super(limit, h);
    }

    @Override
    public Nodo ricerca(Nodo iniziale) {
        time = System.currentTimeMillis();
        List<Nodo> successori = successori(iniziale);
        Nodo migliore = null;
        float valore = NINF;
        float alpha=NINF;
        float beta=INF;
        for(Nodo n : successori){
            float valoreFiglio = minmaxab(n,false,alpha,beta);
            if(valore < valoreFiglio) {
                valore = valoreFiglio;
                migliore = n;
                if(valore>=beta)break;
                alpha = Math.max(alpha,valore);
            }
        }
        flag = false;
        return  migliore;
    }

    public boolean configurazioneFinale(Nodo n ){
        boolean condizione = false;
        byte[] stato = n.getStato();
        int enemycolor = (colore==0)?1:0;
        int offset = 48;
        for(int i = 0;i < 8; i++){
            if((stato[offset*colore+i]==2*colore+1)||(stato[offset*enemycolor+i]==2*enemycolor+1))condizione=true;
        }
        return condizione;
    }

    private float minmaxab(Nodo iniziale,boolean massimizzatore,float alpha, float beta){
        List<Nodo> successori = successori(iniziale);
        float alphaPrimo = alpha;
        float betaPrimo = beta;
        long now = System.currentTimeMillis();
        if(now-time > 800){
            flag = true;
        }
        if(terminal(successori,iniziale)||limiteRaggiunto(iniziale) || flag){
            //System.out.println("Livello : " + iniziale.getLivello());
            return heuristic(iniziale);
        }
        if(massimizzatore){
            float valore=NINF;
            for(Nodo s : successori){
                float valoreFiglio = minmaxab(s,false,alphaPrimo,beta);
                valore = Math.max(valore,valoreFiglio);
                if(valore >= beta)break;
                alphaPrimo = Math.max(alphaPrimo,valore);
            }
            return valore;
        }
        float valore = INF;
        for(Nodo s : successori){
            float valoreFiglio = minmaxab(s,true,alpha,betaPrimo);
            valore = Math.min(valore,valoreFiglio);
            if(valore <= alpha)break;
            betaPrimo = Math.min(betaPrimo,valore);
        }
        return valore;
    }
}