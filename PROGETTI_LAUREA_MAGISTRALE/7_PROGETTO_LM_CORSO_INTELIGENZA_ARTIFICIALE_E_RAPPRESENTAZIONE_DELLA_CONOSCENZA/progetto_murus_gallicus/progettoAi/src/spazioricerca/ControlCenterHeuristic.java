package spazioricerca;

public class ControlCenterHeuristic extends Heuristic {
    public ControlCenterHeuristic(float peso) {
        super(peso);
    }

    @Override
    public float calcola(Nodo n,int colore) {
        int enemy_color=(colore==0)?1:0;
        float torri_avversarie=0;
        float torri_nostre = 0;
        byte[]stato = n.getStato();
        /*for(int j = 18 ; j<38; j++){
            if(j%8==6) j+=4; // nel caso mi trovo dopo il bordo destro del quadrato(22,30,38)
            if(stato[j]==(colore*2)+2){
                torri_nostre++;
            }
            else if(stato[j]==(enemy_color*2)+2){
                torri_avversarie++;
            }
        }*/
        int [] quadrato={18,19,20,21,
                26,27,28,29,
                34,35,36,37};
        for(int i = 0 ; i<quadrato.length; i++){
            int var=stato[quadrato[i]];
            if(var==(colore*2)+2){
                torri_nostre++;
            }
            else if(var==(enemy_color*2)+2){
                torri_avversarie++;
            }
        }
        return (torri_nostre-torri_avversarie)/(torri_nostre+torri_avversarie+1);
    }
}
