package player;

import spazioricerca.*;
import java.util.*;
public class Giocatore implements Comparable<Giocatore>{

    public int vittorie_bianche = 0;
    public int vittorie_nere = 0;
    public int mosse = 0;
    public int valore = 0;


    public int indice = 0;
    public ConcretePlayer c;
    public float[] list;

    public Giocatore(float[] lista){
        //Utilizzare questo e non quello di sotto per la vecchia nuova
		/*float pesoBalance=lista[0];
        float pesoControlCenter=lista[1];
        float pesoEnemyNm=lista[2];
        float pesoDistance=lista[3];
        float pesoDistanceEnemy=lista[4];
        float pesoVictory=lista[5];
		float pesoDefeat=lista[6];
		Complessive_Heuristic l=new Complessive_Heuristic(pesoBalance,pesoControlCenter,pesoEnemyNm,pesoDistance,pesoDistanceEnemy,pesoVictory,pesoDefeat);*/

        //Utilizzare questo e non quello di sopra per la vecchia versione
        list = lista;
        List<Heuristic> l = new LinkedList<>();
        l.add(new BalanceHeuristic(lista[0]));
        l.add(new ControlCenterHeuristic(lista[1]));
        l.add(new EnemyNmHeuristic(lista[2]));
        l.add(new DistanceHeuristic(lista[3]));
        l.add(new DistanceEnemyHeuristic(lista[4]));
        l.add(new VictoryHeuristic(lista[5]));
        l.add(new DefeatHeuristic(lista[6]));

        Ricerca mmab = new MinMaxAlphaBeta(6,l);
        c = new ConcretePlayer(mmab);
    }

    public void setVictoryZero(){

        vittorie_bianche = 0;
        vittorie_nere = 0;
    }

    public int getVictoryBianche(){
        return vittorie_bianche;
    }

    public int getVictoryNere(){ return  vittorie_nere;}

    public void setVictoriesBianche(int v){
        vittorie_bianche = v;
    }

    public void setVictoriesNere(int v){vittorie_nere = v;}

    @Override
    public int compareTo(Giocatore o) {
        int vittorie = o.getVictoryBianche()+o.getVictoryNere();
        if(this.vittorie_bianche+this.vittorie_nere > vittorie){
            return 1;
        }else if (this.vittorie_bianche+this.vittorie_nere == vittorie){
            return 0;
        }else{
            return -1;
        }
    }

}
