package spazioricerca;

public class BalanceHeuristic extends Heuristic{
    public BalanceHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n, int colore) {
        byte[]stato = n.getStato();
        int coloreAmico = colore;
        float num_torri = 0;
        float num_pedine = 0;
        float totale = 0;
        for(int i=0;i<56;i++) {
            if (stato[i] == coloreAmico * 2 + 2) {
                totale++;
                num_torri++;
            }
            if (stato[i] == coloreAmico * 2 + 1) {
                totale++;
                num_pedine++;
            }
        }
        return 1 - (Math.abs((num_torri - num_pedine))/ totale);
    }
}
