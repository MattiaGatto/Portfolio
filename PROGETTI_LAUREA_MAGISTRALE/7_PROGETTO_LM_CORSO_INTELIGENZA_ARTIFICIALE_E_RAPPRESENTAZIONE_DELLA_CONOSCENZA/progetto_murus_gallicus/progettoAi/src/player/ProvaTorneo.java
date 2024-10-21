package player;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

import static player.GeneticAlgorithmV2.sfida2;

public class ProvaTorneo {

    private static LinkedList<Giocatore> giocatori;

    public static void main(String[] args){

        giocatori = new LinkedList<Giocatore>();

        init();

        sfida();

        for(int i = 0; i < giocatori.size();i++){
            System.out.println("Giocatore " + i + " vittorie_bianco: " + giocatori.get(i).vittorie_bianche +
                    " vittorie_nere: " + giocatori.get(i).vittorie_nere);
        }

    }

    public static void init(){

        float[] f1 = {(float) 1.8697625, (float)4.6499324, (float) 3.4835224,(float) 7.1514406,(float) 6.381759,1,1};
        add(f1);

        float[] f2 = {(float) 1.8697625, (float)4.6499324, (float) 7.4835224,(float) 8.1514406,(float) 15.381759,1,1};
        add(f2);

        float[] f3 = {(float) 3.0184042, (float)5.978422, (float) 3.6657076,(float) 1.7083182,(float) 7.3918176,(float) 1.2442861,(float) 8.938698};
        add(f3);

        float[] f4 = {(float) 0.89322084, (float)6.4082384, (float) 9.170627,(float) 7.470046,(float) 6.827063,(float) 6.106145,(float) 3.5109901};
        add(f4);

        float[] f5 = {(float) 3.2568, (float)4.70302, (float) 6.3188887,(float) 8.389069,(float) 5.263301,(float) 8.938097 ,(float) 6.300759 };
        add(f5);

        float[] f6 = {(float) 4.44, (float)1.92, (float) 4.76,(float) 7.75,(float) 9.23,(float) 0.65,(float) 2.91};
        add(f6);

        float[] f7 = {(float) 3.6341228, (float)5.991832, (float) 4.523854,(float) 6.452294,(float) 8.682886,(float) 9.083551, (float) 1.5404351};
        add(f7);

        float[] f8 = {(float) 8.960916, (float)6.1064043, (float) 2.0234387,(float) 7.094306,(float) 8.66806,(float) 0.87527454 ,(float) 6.3903065};
        add(f8);

        float[] f9 = {(float) 2.2866254, (float)6.8998575, (float) 6.2016573,(float) 8.439304,(float) 6.843722,(float) 8.317433 ,(float) 8.438092};
        add(f9);

        float[] f10 = {(float) 9.619126, (float)3.26401, (float) 9.925811,(float) 7.7241936,(float) 7.2159414,(float) 9.038208 ,(float) 7.0082307};
        add(f10);

        float[] f11 = {(float) 4.1022058, (float)8.1695, (float) 1.9029691,(float) 8.130461,(float) 5.1229515,(float) 0.6707399 ,(float) 6.5719083};
        add(f11);

        float[] f12 = {(float) 8.3492565, (float)6.0719824, (float) 2.3965244,(float) 6.6219044,(float) 6.3418083,(float) 8.130581 ,(float) 9.305742};
        add(f12);
    }

    public static void add(float[] f){
        Giocatore gc = new Giocatore(f);
        giocatori.add(gc);

    }

    public static void sfida(){

        for (int i = 0; i < 12;i++){
            Giocatore giocatore1 = giocatori.get(i);

            for(int j = i+1; j < 12;j++){

                Giocatore giocatore2 = giocatori.get(j);

                System.out.println("Giocatore " + i + " vs Giocatore " + j);

                sfida2(giocatore2,giocatore1); //andata

                if(giocatore1.c.vittoria()){
                    giocatore1.setVictoriesBianche(giocatore1.getVictoryBianche()+1);

                }else{
                    giocatore2.setVictoriesNere(giocatore2.getVictoryNere()+1);

                }

                System.out.println("Giocatore " + j + " vs Giocatore " + i);

                sfida2(giocatore1,giocatore2); //ritorno


                if(giocatore1.c.vittoria()){
                    giocatore1.setVictoriesNere(giocatore1.getVictoryNere()+1);

                }else{
                    giocatore2.setVictoriesBianche(giocatore2.getVictoryBianche()+1);

                }

            }
        }

    }
}
