package controller;

import player.Player;
import player.ConcretePlayer;
import spazioricerca.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerCommunicator {
	
	private Player p;
	private int port;
	private Socket s;
	private PrintWriter writer;
	private BufferedReader reader;

	private static float b1 = (float) 4.1022058;
	private static float b2 = (float) 8.1695;
	private static float b3 = (float) 1.9029691;
	private static float b4 = (float) 8.130461;
	private static float b5 = (float)  5.1229515;
	private static float b6 = (float) 0.6707399;
	private static float b7 = (float) 6.5719083;

	private static float n1 = (float) 3.0184042;
	private static float n2= (float) 5.978422;
	private static float n3= (float) 3.6657076;
	private static float n4= (float) 1.7083182;
	private static float n5= (float) 7.3918176;
	private static float n6= (float) 1.2442861;
	private static float n7= (float) 8.938698;

	private static ConcretePlayer cp;
	private static int contatore;

	public ServerCommunicator(Player p,int port){
		this.p = p;
		this.port = port;
		try{
			s = new Socket("localhost",port);
		}
		catch (Exception e){
			e.printStackTrace();;
		}
	}

	private void start()throws IOException{
		OutputStream w = s.getOutputStream();
		writer = new PrintWriter(w,true);
		InputStreamReader r = new InputStreamReader(s.getInputStream());
		reader = new BufferedReader(r);
	}
	private void sendToServer(String message)throws IOException{
		writer.println(message);
		p.aggiorna();
		/*if(contatore > 25){
			cp.algorithm.setLimit(7);
		}*/
		receiveFromServer();
	}

	private void receiveFromServer()throws IOException{
		handleMessage(reader.readLine());
	}
	
	private void init(String color) {
		List<Heuristic> l = new LinkedList<>();
		if(color.equals("Black")){
			/*
			l.add(new BalanceHeuristic((float)0.89322084 ));
			l.add(new ControlCenterHeuristic((float)  6.4082384 ));
			l.add(new EnemyNmHeuristic((float) 9.170627 ));
			l.add(new DistanceHeuristic((float) 7.470046));
			l.add(new DistanceEnemyHeuristic((float) 6.827063  ));
			l.add(new VictoryHeuristic((float) 6.106145 ));
			l.add(new DefeatHeuristic((float) 3.5109901 ));
			 */

			l.add(new BalanceHeuristic(n1 ));
			l.add(new ControlCenterHeuristic(  n2 ));
			l.add(new EnemyNmHeuristic( n3 ));
			l.add(new DistanceHeuristic( n4));
			l.add(new DistanceEnemyHeuristic( n5  ));
			l.add(new VictoryHeuristic( n6 ));
			l.add(new DefeatHeuristic( n7 ));

			Ricerca mmab = new MinMaxAlphaBeta(6,l);
			cp.setRicerca(mmab);
		}else{

			l.add(new BalanceHeuristic(b1));
			l.add(new ControlCenterHeuristic( b2));
			l.add(new EnemyNmHeuristic(b3));
			l.add(new DistanceHeuristic( b4));
			l.add(new DistanceEnemyHeuristic( b5));
			l.add(new VictoryHeuristic( b6));
			l.add(new DefeatHeuristic(b7));

			Ricerca mmab = new MinMaxAlphaBeta(6,l);
			cp.setRicerca(mmab);
		}
		p.setColor(color);
		p.initBoard();
		//Altre eventuali operazioni di inizializzazione da aggiungere dopo o nel metodo initBoard
	}
	
	private void opponentMove(String move) {
		String[] splitted = move.split(",");
		p.opponentMove(splitted[0],splitted[1]);
	}
	
	private void myTurn() throws IOException {

		sendToServer(p.myMove());
	}
	
	private void termina() {
		//terminare la comunicazione
	}
	
	public void handleMessage(String message)throws IOException {
		String[]splitted = message.split(" ");
		System.out.println(message);
		switch(splitted[0]) {
			case "WELCOME":
				this.init(splitted[1]);
				receiveFromServer();
				break;
			case "OPPONENT_MOVE":
				this.opponentMove(splitted[1]);
				receiveFromServer();
				break;
			case "YOUR_TURN":
				this.myTurn();
				break;
			case "ILLEGAL_MOVE":
				this.termina();
				break;
			case "TIMEOUT":
				this.termina();
				System.out.println("Timeout");
				break;
			case "VICTORY":
				this.termina();
				System.out.println("Victory");
				break;
			case "DEFEAT":
				this.termina();
				System.out.println("Defeat");
				break;
			case "MESSAGE":
				receiveFromServer();
				break;
			case "VALID_MOVE":
				receiveFromServer();
				break;
		}	
	}
	public static void main(String[] args) {

		cp = new ConcretePlayer();
		ServerCommunicator sc = new ServerCommunicator(cp,8901);
		contatore = 0;
		try {
			sc.start();
			sc.receiveFromServer();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
