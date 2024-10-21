package player;

import spazioricerca.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProvaBianco {

    public static void main(String[] args) {
        //Utilizzare questo e non quello di sotto per la vecchia nuova
        /*float pesoBalance=((float)1.8697625);
        float pesoControlCenter=((float) 4.6499324);
        float pesoEnemyNm=((float)7.4835224);
        float pesoDistance=((float) 8.1514406);
        float pesoDistanceEnemy=((float) 15.381759);
        float pesoVictory=((float) 1);
        float pesoDefeat=((float) 1);
		Complessive_Heuristic l=new Complessive_Heuristic(pesoBalance,pesoControlCenter,pesoEnemyNm,pesoDistance,pesoDistanceEnemy,pesoVictory,pesoDefeat);
*/
        //Utilizzare questo e non quello di sopra per la vecchia versione
        List<Heuristic> l = new LinkedList<Heuristic>();
        l.add(new BalanceHeuristic((float)4.1022058));
        l.add(new ControlCenterHeuristic((float) 8.1695));
        l.add(new EnemyNmHeuristic((float)1.9029691));
        l.add(new DistanceHeuristic((float) 8.130461));
        l.add(new DistanceEnemyHeuristic((float) 5.1229515));
        l.add(new VictoryHeuristic((float) 0.6707399));
        l.add(new DefeatHeuristic((float)6.5719083));

        MinMaxAlphaBeta m = new MinMaxAlphaBeta(6,l);
        ConcretePlayer c = new ConcretePlayer(m);
        c.initBoard();
        c.setColor("White");

        int contatore = 0;
        Character [] lettere= {'A','B','C','D','E','F','G'};

        while(true){

            if(contatore > 10){
                c.algorithm.setLimit(6);
            }
            System.out.print("   1 2 3 4 5 6 7 8\n");
            for(int i = 0; i < 7;i++){
                System.out.print(lettere[i]+"  ");
                for(int j = 0; j < 8;j++){
                    if(c.scacchiera[i*8+j]==0)System.out.print(" "+ " ");//vuoto
                    /*else if(c.scacchiera[i*8+j]==2)System.out.print("█"+ " ");//torre bianca
                    else if(c.scacchiera[i*8+j]==1)System.out.print("■"+ " ");//pedina bianca
                    else if(c.scacchiera[i*8+j]==4)System.out.print("░"+ " ");//torre nera
                    else if(c.scacchiera[i*8+j]==3)System.out.print("o"+ " ");//pedina nera*/
                    else System.out.print(c.scacchiera[i*8+j] + " ");
                }
                System.out.println();
            }

            System.out.println();
            System.out.println();

            long timestamp = System.currentTimeMillis();
            String f = c.myMove();
            System.out.println(f);
            long now = System.currentTimeMillis() - timestamp;
            System.out.println("Tempo della mossa: " + now + " ms");
            c.aggiorna();
            System.out.print("   1 2 3 4 5 6 7 8\n");
            for(int i = 0; i < 7;i++){
                System.out.print(lettere[i]+"  ");
                for(int j = 0; j < 8;j++){
                    if(c.scacchiera[i*8+j]==0)System.out.print(" "+ " ");//vuoto
                    /*else if(c.scacchiera[i*8+j]==2)System.out.print("█"+ " ");//torre bianca
                    else if(c.scacchiera[i*8+j]==1)System.out.print("■"+ " ");//pedina bianca
                    else if(c.scacchiera[i*8+j]==4)System.out.print("░"+ " ");//torre nera
                    else if(c.scacchiera[i*8+j]==3)System.out.print("o"+ " ");//pedina nera*/
                    else System.out.print(c.scacchiera[i*8+j] + " ");
                }
                System.out.println();
            }

            Scanner s = new Scanner(System.in);
            System.out.println("Scrivi la posizione del pezzo che vuoi muovere:(tipo a1 o A1..) ");
            String act = s.next();
            while (act.matches("[A-G][1-8]")!=true) {
                System.out.println("Scrivi la posizione del pezzo che vuoi muovere: ");
                act = s.next();
            }
            System.out.println("Scrivi la direzione:(tipo [N NE E SE S SW W NW]) ");
            String dir = s.next();
            while (dir.matches("[N E W S]|SE|SW|NE|NW")!=true) {
                System.out.println("Scrivi la direzione: ");
                dir = s.next();
            }
            c.opponentMove(act, dir);
            System.out.println();
            System.out.println();

            contatore++;
        }

    }
}

