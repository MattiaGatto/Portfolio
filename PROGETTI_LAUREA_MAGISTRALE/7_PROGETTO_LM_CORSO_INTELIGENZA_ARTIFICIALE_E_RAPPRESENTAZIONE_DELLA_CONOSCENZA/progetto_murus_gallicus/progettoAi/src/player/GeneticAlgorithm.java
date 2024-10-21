package player;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm {

    List<Giocatore> perdenti;
    List<Giocatore> giocatori;

    public GeneticAlgorithm(){
        perdenti=new LinkedList<>();
        giocatori=new LinkedList<>();
        for(int i = 0; i < 256;i++){
            float weight1 = (float) Math.random() * 10;
            float weight2 = (float) Math.random() * 10;
            float weight3 = (float) Math.random() * 10;
            float weight4 = (float) Math.random() * 10;
            float weight5 = (float) Math.random() * 10;
            float weight6 = (float) Math.random() * 10;
            float weight7 = (float) Math.random() * 10;
            float[] f = {weight1,weight2,weight3,weight4,weight5,weight6,weight7};
            Giocatore g = new Giocatore(f);
            g.indice = i;
            giocatori.add(g);
        }

    }

    public static void main(String[] args) {
        System.out.println("INIZIO GIRONE VINCITORI");
        GeneticAlgorithm g = new GeneticAlgorithm();
        Giocatore vincitore = g.algorithm();

        System.out.println("FINE GIRONE VINCITORI");
        System.out.println("VINCITORE GIOCATORE "+vincitore.indice);
        for(int i = 0; i < 5;i++){
            System.out.println(vincitore.list[i] + " ");
        }
        System.out.println();
        fase=1;

        System.out.println("INIZIO GIRONE PERDENTI");
        g.giocatori = new LinkedList<>(g.perdenti);
        g.giocatori.add(g.perdenti.get(0));
        Giocatore vincitore_perdenti = g.algorithm();

        System.out.println("FINE GIRONE PERDENTI");
        System.out.println("VINCITORE DEI PERDENTI GIOCATORE "+vincitore_perdenti.indice);
        for(int i = 0; i < 5;i++){
            System.out.println(vincitore_perdenti.list[i]+ " ");
        }
        System.out.println();
        fase=1;

        g.giocatori.clear();
        g.giocatori.add(vincitore);
        g.giocatori.add(vincitore_perdenti);
        System.out.println("FINALE: (Vincente fra tutti VS Vincente fra i perdenti): ");
        Giocatore numero_1 = g.algorithm();
        System.out.println("FINE FINALE: ");
        System.out.println("VINCITORE DEI PERDENTI GIOCATORE "+numero_1.indice);
        for(int i = 0; i < 5;i++){
            System.out.println(numero_1.list[i] + " ");
        }
    }


    private static int fase=1;
    public Giocatore algorithm (){
        while(giocatori.size()!=1) {
            System.out.println("Inizio "+fase+" FASE.");
            for (int i = 0; i < giocatori.size(); i = i + 2) {
                Giocatore g1 = giocatori.get(i);
                Giocatore g2 = giocatori.get(i+1);
                Giocatore vincitore = sfida(g1, g2);
                if (g1.c.vittoria()) {
                    System.out.println("Giocano " + g1.indice + " vs " + g2.indice + " Vince " + g1.indice);
                } else {
                    System.out.println("Giocano " + g1.indice + " vs " + g2.indice + " Vince " + g2.indice);
                }
                if (vincitore.equals(g2)) {
                    perdenti.add(g1);
                    giocatori.set(i, null);
                } else {
                    perdenti.add(g2);
                    giocatori.set(i + 1, null);
                }
            }
            for (int i = 0; i < 128; i++) {
                for(int j = 0; j < giocatori.size();j++) {
                    if (giocatori.get(j) == null) {
                        giocatori.remove(j);
                        break;
                    }
                }
            }
            System.out.println("Fine "+fase+" FASE.\n");
            fase+=1;
        }
        return giocatori.get(0);
    }


    public Giocatore sfida(Giocatore g1, Giocatore g2){
        g1.c.initBoard();
        g1.c.setColor("Black");
        g2.c.initBoard();
        g2.c.setColor("White");

        while( !g1.c.vittoria() && !g2.c.vittoria()){
            String c = g2.c.myMove();
            g2.c.aggiorna();
            String mossa = c.split(" ")[1].split(",")[0];
            String dir = c.split(" ")[1].split(",")[1];
            g1.c.opponentMove(mossa,dir);

            String d= g1.c.myMove();
            g1.c.aggiorna();
            String mossa1 = d.split(" ")[1].split(",")[0];
            String dir1 = d.split(" ")[1].split(",")[1];
            g2.c.opponentMove(mossa1,dir1);
        }


        if(g1.c.vittoria())
            return g1;
        else
            return g2;
    }


}