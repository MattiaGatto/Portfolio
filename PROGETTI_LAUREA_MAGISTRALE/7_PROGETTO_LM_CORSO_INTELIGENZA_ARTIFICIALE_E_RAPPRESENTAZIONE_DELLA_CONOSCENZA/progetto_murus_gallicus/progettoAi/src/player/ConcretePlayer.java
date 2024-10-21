package player;

import spazioricerca.*;

import java.util.LinkedList;


public class ConcretePlayer implements Player{

    public long time;

    int enemyColor;
    int ourColor;
    byte[] scacchiera;
    byte[] mossaAttuale;

    /*
     1 = pedina bianca
     2 = torre bianca
     3 = pedina nera
     4 = torre nera
    */
    enum conversion { A,B,C,D,E,F,G}
    enum direzione { N,NE,E,SE,S,SW,W,NW}
    public Ricerca algorithm;

    public ConcretePlayer(){}

    public ConcretePlayer(Ricerca algorithm){
        this.algorithm = algorithm;
    }

    public void setRicerca(Ricerca algorithm){
        this.algorithm = algorithm;
    }
    @Override
    public void setColor(String color) {
        this.ourColor = (color.equals("Black")) ? 1 : 0;
        this.enemyColor = (color.equals("Black")) ? 0 : 1;
        this.algorithm.setColore(ourColor);
    }

    @Override
    public void opponentMove(String start,String dir) {

        int actualPosition = convertMossa(start);
        int direction = direzione.valueOf(dir).ordinal();
        aggiornaScacchiera(actualPosition,enemyColor,direction);

    }

    @Override
    public String myMove() {

        //algorithm.setTime(System.currentTimeMillis());

        Nodo attuale = new Nodo();
        attuale.setStato(scacchiera); //creazione del nodo da passare all'algoritmo
        //chiamare l'algoritmo di ricerca
        attuale.setLivello(0);
        attuale.setColor(ourColor);
        Nodo prossimo = algorithm.ricerca(attuale); //ricerca del nodo goal
        mossaAttuale = prossimo.getMossa(); //prendo la mossa che identifica questo nodo

        String posizione = deconvertMossa(mossaAttuale[0]); //trasformo la posizione dell'array nel punto della scacchiera

        String dir = direzione.values()[mossaAttuale[1]].toString();

        return "MOVE " + posizione + "," + dir; //restituisco la mossa effettuata
    }

    public int contaTorri(){
        int k = 0;
        for(int i = 0; i < 56;i++){
            if(scacchiera[i] == 2 || scacchiera[i] == 4){
                k++;
            }
        }
        return k;
    }

    @Override
    public void initBoard() {
        scacchiera = new byte[]{4, 4, 4, 4, 4, 4, 4, 4,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                2, 2, 2, 2, 2, 2, 2, 2};
    }

    public int convertMossa (String mossa){
        String riga = Character.toString(mossa.charAt(0)); //prendo il primo elemento es. A,B,C ecc...
        int colonna =  Character.getNumericValue(mossa.charAt(1));  //prendo il secondo elemento 1,2,3 ecc...

        int numeroRiga = conversion.valueOf(riga).ordinal(); //converto la lettera nel numero della riga

        return numeroRiga*8 + (colonna-1); //restituisco la posizione nell'array
    }

    public String deconvertMossa (int mossa){

        int colonna = (mossa % 8) + 1;
        int riga = mossa/8;

        String mossaFinale = conversion.values()[riga].toString();

        return mossaFinale + colonna;

    }

    public void add(int attuale,int prossima, int successiva,int color){

        int pedina = 2*color+1;
        int torre = 2*color+2;

        int enemy = (color == 0)? 1:0;

        if( scacchiera[prossima] == 0 && scacchiera[successiva] == 0){
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) pedina;
            scacchiera[successiva] = (byte) pedina;
        }else if ( scacchiera[prossima] == pedina && scacchiera[successiva] == pedina){
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) torre;
            scacchiera[successiva] = (byte) torre;
        }else if ( scacchiera[prossima] == pedina && scacchiera[successiva] == 0) {
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) torre;
            scacchiera[successiva] = (byte) pedina;
        }else if ( scacchiera[prossima] == 0 && scacchiera[successiva] == pedina){
            scacchiera[attuale] = 0;
            scacchiera[prossima] = (byte) pedina;
            scacchiera[successiva] = (byte) torre;
        }else if ( scacchiera [prossima] == 2*enemy+1){
            scacchiera[attuale] = (byte) pedina;
            scacchiera[prossima] = 0;
        }


    }

    @Override
    public void aggiorna(){
        int actualPosition = mossaAttuale[0];
        int direction = mossaAttuale[1];

        aggiornaScacchiera(actualPosition,ourColor,direction);
    }

    public void aggiornaScacchiera(int actualPosition,int colore,int direction){
        switch(direction) {
            case 0: //nord
                add(actualPosition,actualPosition-8,actualPosition-16,colore);
                break;
            case 1: //nord-est
                add(actualPosition,actualPosition-7,actualPosition-14,colore);
                break;
            case 2: //est
                add(actualPosition,actualPosition+1,actualPosition+2,colore);
                break;
            case 3: //sud-est
                add(actualPosition,actualPosition+9,actualPosition+18,colore);
                break;
            case 4: //sud
                add(actualPosition,actualPosition+8,actualPosition+16,colore);
                break;
            case 5: //sud-ovest
                add(actualPosition,actualPosition+7,actualPosition+14,colore);
                break;
            case 6: //ovest
                add(actualPosition,actualPosition-1,actualPosition-2,colore);
                break;
            case 7: //nord-ovest
                add(actualPosition,actualPosition-9,actualPosition-18,colore);
                break;
        }
    }

    public boolean vittoria() {
        if (ourColor == 0) {
            for (int i = 0; i < 8; i++) {
                if (scacchiera[i] == 2*ourColor+1) {
                    return true;
                }
            }
        } else {
            for (int i = 48; i < 56; i++) {
                if (scacchiera[i] == 2*ourColor+1) {
                    return true;
                }
            }
        }
        int torri_avv = 0;
        for(int i = 0;i < 56;i++){
            if(scacchiera[i] == 2*enemyColor+2) {
                torri_avv++;
            }
        }
        if(torri_avv == 0){
            return true;
        }
        return false;
    }



}