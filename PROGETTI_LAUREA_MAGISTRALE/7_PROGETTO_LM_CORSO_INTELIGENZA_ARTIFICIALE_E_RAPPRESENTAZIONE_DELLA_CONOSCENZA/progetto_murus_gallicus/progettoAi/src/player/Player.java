package player;

public interface Player {
	
	public void setColor(String color); //Determina il colore
	
	public void opponentMove(String start,String dir); //Aggiorna lo stato con la mossa dell'avversario
	
	public String myMove(); //Ricostruisce il path a partire dal goal e determinare la prossima mossa
	
	public void initBoard(); //Inizializza la scacchiera

	public void aggiorna(); //Aggiorna scacchiera

}
