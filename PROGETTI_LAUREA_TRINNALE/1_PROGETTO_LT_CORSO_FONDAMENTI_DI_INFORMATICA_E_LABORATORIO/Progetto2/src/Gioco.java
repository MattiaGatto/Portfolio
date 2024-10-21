public class Gioco extends Campo {
	
	public int caselleScoperte=0;
	private boolean mina=false;
	public Gioco(Difficolta d){
		super(d);
	}
	public boolean scopriCasella(Casella c){
		if (!(c.isScoperta())){
			c.setScoperta(true);
			caselleScoperte+=1;
			if (c.getValore()==-1)
				mina=true;
			return true;
		}
		return false;
	}
	public boolean vittoria(){
		if(mina==false&&(caselleScoperte+nMine==NCASELLE)){
			return true;
		}
		return false;
	}
	public boolean sconfitta(){
		return mina;
	}

}
