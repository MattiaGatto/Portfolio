package player;

import sun.awt.image.ImageWatched;

import java.util.*;

public class GeneticAlgorithmV2 {


    static List<Giocatore> giocatori;
    static List<Giocatore> vincenti;

    public static void main(String[] args) {

        giocatori = init(20);


        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis();

        int generazione = 0;

        while (true) {

            sfida();

            System.out.print("Generation: " + generazione + " Fittest: " );
            Giocatore g = getFittest();
            for(int i = 0; i < g.list.length; i++ ){
                System.out.print( g.list[i] + " ");
            }
            System.out.println();
            System.out.println("Vittorie Bianche: " + g.getVictoryBianche() + " Vittorie Nere: " + g.getVictoryNere());


            vincenti = new LinkedList<>();

            selection();

            crossover();

            if(Math.random() < 0.1){
                mutation();
            }

            swap();

            setFitnessZero();

            t2 = System.currentTimeMillis();

            generazione++;

        }
    }

    public static void setFitnessZero(){
        for(int i = 0;i < giocatori.size();i++){
            giocatori.get(i).setVictoryZero();
        }
    }

    public static void swap(){

        giocatori.clear();
        for(int i = 0; i < vincenti.size();i++){
            giocatori.add(vincenti.get(i));
        }
        vincenti.clear();

    }

    public static void mutation(){

        for (int i = 10; i < vincenti.size();i++){

            Random r = new Random();
            int mutationPoint = r.nextInt(vincenti.get(0).list.length);

            Giocatore mutationPlayer = vincenti.get(i);
            Giocatore vincenteGenerazione = vincenti.get(0);

            float[] genes = mutationPlayer.list;

            if(vincenteGenerazione.list[mutationPoint] > genes[mutationPoint]){
                genes[mutationPoint] += (vincenteGenerazione.list[mutationPoint] - genes[mutationPoint])
                        /(vincenteGenerazione.list[mutationPoint] + genes[mutationPoint]);
            }else{
                genes[mutationPoint] -= (vincenteGenerazione.list[mutationPoint] - genes[mutationPoint])
                        /(vincenteGenerazione.list[mutationPoint] + genes[mutationPoint]);
            }

            vincenti.set(i,new Giocatore(genes));

        }

    }

    public static void crossover(){

        List<Giocatore> lg = new LinkedList<>();

        for(int i = 0;i < (vincenti.size()/2);i++){
            Giocatore genitore1 = vincenti.get(i);
            Giocatore genitore2 = vincenti.get(vincenti.size()-i-1);

            Random rand = new Random();

            int crossoverPointstart = rand.nextInt(genitore1.list.length);
            int crossoverPointend = rand.nextInt(genitore1.list.length);

            int min = Math.min(crossoverPointend,crossoverPointstart);
            int max = Math.max(crossoverPointend,crossoverPointstart);

            float[] genifiglio1 = new float[genitore1.list.length];

            float[] genifiglio2 = new float[genitore2.list.length];


            for(int j = 0; j < genitore1.list.length;j++){

                if(j < min){
                    genifiglio1[j] = genitore1.list[j];
                    genifiglio2[j] = genitore2.list[j];
                }else if(j>= min && j <= max){
                    genifiglio1[j] = genitore2.list[j];
                    genifiglio2[j] = genitore1.list[j];
                }else{
                    genifiglio1[j] = genitore1.list[j];
                    genifiglio2[j] = genitore2.list[j];
                }

            }

            Giocatore figlio1 = new Giocatore(genifiglio1);
            Giocatore figlio2 = new Giocatore(genifiglio2);

            lg.add(figlio1);
            lg.add(figlio2);

        }

        for(int i = 0; i < lg.size();i++){
            vincenti.add(lg.get(i));
        }

    }

    public static void selection(){
        for(int i = 0; i < 10;i++){
            vincenti.add(giocatori.get(i));
        }
    }

    public static Giocatore getFittest(){
        Collections.sort(giocatori,Collections.reverseOrder());
        return giocatori.get(0);
    }

    public static void sfida(){

        for(int i = 0; i < 20;i++){
            Giocatore giocatore1 = giocatori.get(i);

            for(int j = i+1; j < 20; j++){
                Giocatore giocatore2 = giocatori.get(j);

                sfida2(giocatore2,giocatore1); //andata

                if(giocatore1.c.vittoria()){
                    giocatore1.setVictoriesBianche(giocatore1.getVictoryBianche()+1);
                }else{
                    giocatore2.setVictoriesNere(giocatore2.getVictoryNere()+1);
                }

                sfida2(giocatore1,giocatore2); //ritorno

                if(giocatore1.c.vittoria()){
                    giocatore1.setVictoriesNere(giocatore1.getVictoryNere()+1);

                }else{
                    giocatore2.setVictoriesBianche(giocatore2.getVictoryBianche()+1);
                }


            }
            System.out.println("Concluse partite giocatore: " + i);
        }

    }

    public static void sfida2(Giocatore g1, Giocatore g2){
        g1.c.initBoard();
        g1.c.setColor("Black");
        g2.c.initBoard();
        g2.c.setColor("White");

        g1.mosse = 0;
        g2.mosse = 0;

        while( !g1.c.vittoria() && !g2.c.vittoria()){
            String c = g2.c.myMove();
            g2.c.aggiorna();
            String mossa = c.split(" ")[1].split(",")[0];
            String dir = c.split(" ")[1].split(",")[1];
            g1.c.opponentMove(mossa,dir);
            g2.mosse++;

            if(g2.c.vittoria()){
                break;
            }

            String d= g1.c.myMove();
            g1.c.aggiorna();
            String mossa1 = d.split(" ")[1].split(",")[0];
            String dir1 = d.split(" ")[1].split(",")[1];
            g2.c.opponentMove(mossa1,dir1);
            g1.mosse++;
        }

    }

    public static LinkedList<Giocatore> init(int x){

        LinkedList<Giocatore> g = new LinkedList<>();
        for(int i = 0; i < x;i++){
            float weight1 = (float) Math.random() * 10;
            float weight2 = (float) Math.random() * 10;
            float weight3 = (float) Math.random() * 10;
            float weight4 = (float) Math.random() * 10;
            float weight5 = (float) Math.random() * 10;
            float weight6 = (float) Math.random() * 10;
            float weight7 = (float) Math.random() * 10;
            float[] f = {weight1,weight2,weight3,weight4,weight5,weight6,weight7};
            Giocatore gc = new Giocatore(f);
            gc.indice = i;
            g.add(gc);
        }

        return g;
    }

    public static void tournament(){

    }

}
