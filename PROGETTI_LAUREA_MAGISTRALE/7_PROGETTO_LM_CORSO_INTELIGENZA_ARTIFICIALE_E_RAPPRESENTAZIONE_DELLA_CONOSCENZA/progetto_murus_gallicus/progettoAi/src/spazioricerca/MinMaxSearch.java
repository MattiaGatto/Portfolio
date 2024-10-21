package spazioricerca;

import java.util.List;
import java.util.Stack;

public class MinMaxSearch extends Ricerca {

    private Stack<Nodo>nodes = new Stack<>();

    public MinMaxSearch(int limit,List<Heuristic> h){ //, NewHeuristic) {
        super(limit,h);
    }

    @Override
    public Nodo ricerca(Nodo iniziale) {
        List<Nodo> successori = successori(iniziale);
        Nodo migliore = null;
        float valore = NINF;
        for(Nodo n : successori){
            float valoreFiglio = minmax(n,false);
            valore = Math.max(valore,valoreFiglio);
            if(valore == valoreFiglio)migliore = n;
        }
        return  migliore;
    }

    private float minmax(Nodo iniziale, boolean massimizzatore) {
        List<Nodo> successori = successori(iniziale);
        if(limiteRaggiunto(iniziale)||terminal(successori,iniziale)){
            return heuristic(iniziale);
        }
        if(massimizzatore){
            float valore=NINF;
            for(Nodo s : successori){
                float valoreFiglio = minmax(s,false);
                valore = Math.max(valore,valoreFiglio);
            }
            return valore;
        }
        float valore = INF;
        for(Nodo s : successori){
            float valoreFiglio = minmax(s,true);
            valore = Math.min(valore,valoreFiglio);
        }
        return valore;
    }

}
