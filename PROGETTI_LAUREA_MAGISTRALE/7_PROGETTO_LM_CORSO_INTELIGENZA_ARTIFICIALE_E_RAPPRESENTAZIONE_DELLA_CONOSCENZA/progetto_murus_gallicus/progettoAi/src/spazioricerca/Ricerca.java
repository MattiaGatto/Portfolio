package spazioricerca;

import controller.ServerCommunicator;

import java.time.chrono.HijrahEra;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Ricerca {

    private int limit;
    //versione nuova
    //protected NewHeuristic h;
    //versione vecchia
    protected List<Heuristic> h;
    protected int colore;


    public Ricerca(int limit,List<Heuristic> h){ //NewHeuristic h){
        this.limit = limit;
        this.h=h;
    }

    //versione vecchia
    public List<Heuristic> getH(){
        return h;
    }
    //versione nuova
    /*public NewHeuristic getH(){
        return h;
    }*/

    protected static int INF=1000;
    protected static int NINF=-1000;
    protected static int VOID=-100;

    public abstract Nodo ricerca(Nodo iniziale);

    //versione vecchia
    public float heuristic(Nodo nodo){
        float valore= 0;
        for(Heuristic heu:this.h){
            float val = heu.calcola(nodo,colore)*heu.peso;
            valore = valore + val;
        }
        return valore;
    }
    //versione nuova
    /*public float heuristic(Nodo nodo){
        float valore= 0;
        valore=h.calcola(nodo,colore);
        return valore;
    }*/

    public int getLimit(){return limit;}
    public void setLimit(int limit){ this.limit=limit; }

    public void setColore(int colore){
        this.colore=colore;
    }


    //versione nuova
    //public void setHeuristic(NewHeuristic h){this.h=h;}
    //versione vecchia
    public void setHeuristic(Heuristic h){this.h.add(h);}

    protected boolean limiteRaggiunto(Nodo n){
        return n.getLivello()>=limit;
    }

    protected boolean terminal(List<Nodo>successori,Nodo n){
        boolean condizione = false;
        byte[] stato = n.getStato();
        int enemycolor = (colore==0)?1:0;
        int offset = 48;
        int torri_nostre = 0;
        int torri_avversarie=0;

        for(int i = 0;i < 8; i++){
            if((stato[offset*colore+i]==2*colore+1)||(stato[offset*enemycolor+i]==2*enemycolor+1))condizione=true;
        }
        for(int i=0; i<56; i++){
            if(stato[i]==2*colore+2)torri_nostre++;
            if(stato[i]==2*enemycolor+2)torri_avversarie++;
        }
        if(torri_nostre==0||torri_avversarie==0)condizione=true;
        return condizione || successori.isEmpty();
    }

    protected List<Nodo>successori(Nodo n){
        LinkedList<Nodo> figli= new LinkedList<>();
        byte[]stato=n.getStato();

        int enemy_color = (n.getColor() == 0) ? 1:0;

        byte torre = (byte) (2*n.getColor()+2);
        for ( int posizione_attuale=0; posizione_attuale< stato.length;posizione_attuale++) {
            if (stato[posizione_attuale]==torre) {
                for (int direzione_possibile=0; direzione_possibile<8;direzione_possibile++){
                    Nodo possibile_figlio=mossaPossibile(posizione_attuale,direzione_possibile,stato,n.getColor());
                    if (possibile_figlio!=null){
                        byte[] mossa={(byte)posizione_attuale,(byte)direzione_possibile};
                        possibile_figlio.setMossa(mossa);
                        possibile_figlio.setPredecessore(n);
                        possibile_figlio.setLivello(n.getLivello()+1);
                        possibile_figlio.setColor(enemy_color);
                        figli.addFirst(possibile_figlio);

                    }
                }
            }
        }
        return figli;
    }

    private Nodo mossaPossibile(int posizione_attuale,int mossa_possibile,byte[]stato,int colore_Pedina){
        byte[] nuovoStato= Arrays.copyOf(stato,56);
        switch(mossa_possibile) {
            case 0: //nord
                if((posizione_attuale-16)>=0){
                    return add_Mossa_figlio(posizione_attuale,posizione_attuale-8,posizione_attuale-16,nuovoStato,colore_Pedina);
                }
                else if ((posizione_attuale-8)>=0){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale-8,nuovoStato,colore_Pedina);
                }
                else{
                    return null;
                }
            case 1: //nord-est
                if((posizione_attuale-14)>=0 && posizione_attuale%8<6){
                    return add_Mossa_figlio(posizione_attuale, posizione_attuale - 7, posizione_attuale - 14, nuovoStato,colore_Pedina);
                }
                else if ((posizione_attuale-7)>=0 && posizione_attuale%8<7){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale- 7,nuovoStato,colore_Pedina);
                }
                else{
                    return null;
                }
            case 2: //est
                if (posizione_attuale%8<6) {
                    return add_Mossa_figlio(posizione_attuale,posizione_attuale+1,posizione_attuale+2,nuovoStato,colore_Pedina);
                }
                else if (posizione_attuale%8<7){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale+1,nuovoStato,colore_Pedina);
                }
                else {
                    return null;
                }
            case 3: //sud-est
                if (posizione_attuale%8<6&&(posizione_attuale+18)<=55) {
                    return add_Mossa_figlio(posizione_attuale, posizione_attuale + 9, posizione_attuale + 18, nuovoStato,colore_Pedina);
                }
                else if (posizione_attuale%8<7&&(posizione_attuale+9)<=55){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale+9,nuovoStato,colore_Pedina);
                }
                else {
                    return null;
                }
            case 4: //sud
                if ((posizione_attuale+16)<=55) {
                    return add_Mossa_figlio(posizione_attuale, posizione_attuale + 8, posizione_attuale + 16, nuovoStato,colore_Pedina);
                }
                else if ((posizione_attuale+8)<=55){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale+8,nuovoStato,colore_Pedina);
                }
                else {
                    return null;
                }
            case 5: //sud-ovest
                if (posizione_attuale+14<=55 && posizione_attuale%8>1 ) {
                    return add_Mossa_figlio(posizione_attuale, posizione_attuale + 7, posizione_attuale + 14, nuovoStato,colore_Pedina);
                }
                else if ((posizione_attuale+7)<=55&& posizione_attuale%8>0){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale+7,nuovoStato,colore_Pedina);
                }
                else {
                    return null;
                }
            case 6: //ovest
                if (posizione_attuale%8>1){
                    return add_Mossa_figlio(posizione_attuale, posizione_attuale - 1, posizione_attuale - 2, nuovoStato,colore_Pedina);
                }
                else if (posizione_attuale%8>0){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale-1,nuovoStato,colore_Pedina);
                }
                else {
                    return null;
                }
            case 7: //nord-ovest
                if(posizione_attuale%8>1 && (posizione_attuale-18)>=0) {
                    return add_Mossa_figlio(posizione_attuale, posizione_attuale - 9, posizione_attuale - 18, nuovoStato,colore_Pedina);
                }
                else if (posizione_attuale%8>0&& (posizione_attuale-9)>=0){
                    return add_Mossa_figlio_Caso_Sacrificio(posizione_attuale,posizione_attuale-9,nuovoStato,colore_Pedina);
                }
                else {
                    return null;
                }
            default:
                Nodo n=null;
                return n;
        }

    }


    private Nodo add_Mossa_figlio(int attuale,int prossima, int prossima_prossima, byte[]scacchiera,int colore_Pedina){
        Nodo n=new Nodo();
        int pedina = 2*colore_Pedina+1;
        int torre = 2*colore_Pedina+2;
        int enemy = (colore_Pedina == 0)? 1:0;
        if (scacchiera[prossima] == 0 && scacchiera[prossima_prossima] == 0) {
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) pedina;
            scacchiera[prossima_prossima] = (byte) pedina;
            n.setStato(scacchiera);
            return n;
        } else if (scacchiera[prossima] == pedina && scacchiera[prossima_prossima] == pedina) {
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) torre;
            scacchiera[prossima_prossima] = (byte) torre;
            n.setStato(scacchiera);
            return n;
        } else if (scacchiera[prossima] == 0 && scacchiera[prossima_prossima] == pedina) {
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) pedina;
            scacchiera[prossima_prossima] = (byte) torre;
            n.setStato(scacchiera);
            return n;
        } else if (scacchiera[prossima] == pedina && scacchiera[prossima_prossima] == 0) {
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) torre;
            scacchiera[prossima_prossima] = (byte) pedina;
            n.setStato(scacchiera);
            return n;
        } else if ( scacchiera [prossima] == 2*enemy+1) {
            scacchiera[attuale] = (byte) pedina;
            scacchiera[prossima] = 0;
            n.setStato(scacchiera);
            return n;
        }
        else{
            n=null;
        }
        return n;
    }

    private Nodo add_Mossa_figlio_Caso_Sacrificio(int attuale,int prossima, byte[]scacchiera,int colore_Pedina){
        Nodo n=new Nodo();
        int pedina = 2*colore_Pedina+1;
        int enemy = (colore_Pedina == 0)? 1:0;
        if ( scacchiera [prossima] == 2*enemy+1) {
            scacchiera[attuale] = (byte) pedina;
            scacchiera[prossima] = 0;
            n.setStato(scacchiera);
            return n;
        }
        else{
            n=null;
        }
        return n;
    }


}
