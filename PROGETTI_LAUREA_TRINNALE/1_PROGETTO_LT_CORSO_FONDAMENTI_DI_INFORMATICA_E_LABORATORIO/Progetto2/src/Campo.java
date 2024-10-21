import java.util.Random;

import javax.swing.JOptionPane;
public class Campo {
	protected int nMine;
	public static final int NCASELLE=64;
	
	public enum Difficolta{
		FACILE,MEDIA,DIFFICILE,PERSONALIZZATA
	}
	protected Casella[][]campo=new Casella[8][8];
	public Casella[][] getCampo() {
		return campo;
	}
	public void setCampo(Casella[][] campo) {
		this.campo = campo;
	}
	public void creaCampo(Difficolta d){
		int i=0;
		Random random=new Random();
		switch(d){
		case FACILE:nMine=10;
		break;
		case MEDIA:nMine=20;
		break;
		case DIFFICILE:nMine=30;
		break;
		case PERSONALIZZATA:
			String t=JOptionPane.showInputDialog("Insersci un numero di mine a tuo piacere");
			nMine=Integer.parseInt(t);
			while(nMine>=64&&nMine<0){
				t=JOptionPane.showInputDialog("Insersci un numero di mine a tuo piacere ma minore di 64");
				nMine=Integer.parseInt(t);
			}
		break;
		}
		while(i<nMine){
			int r=random.nextInt(8);
			int c=random.nextInt(8);
			if(campo[r][c]==null){
				campo[r][c]=new Casella(-1);
				i++;
			}	
		}
		for(int j=0;j<8;j++)
			for(int z=0;z<8;z++){
				if(campo[j][z]==null){
					campo[j][z]=new Casella(nMineAdiacenti(j,z));
				}
			}
	}
	public int nMineAdiacenti(int r,int c){
		int n=0;
		for(int i=-1;i<2;i++){
			for(int j=-1;j<2;j++){
				if(r+i>=0&&r+i<8&&c+j>=0&& c+j<8) {
					if(campo[r+i][c+j]!=null&&campo[r+i][c+j].getValore() == -1)
						n++;
				}
			}
		}
		return n;
	}
	public Campo(Difficolta d){
		creaCampo(d);
	}
}
