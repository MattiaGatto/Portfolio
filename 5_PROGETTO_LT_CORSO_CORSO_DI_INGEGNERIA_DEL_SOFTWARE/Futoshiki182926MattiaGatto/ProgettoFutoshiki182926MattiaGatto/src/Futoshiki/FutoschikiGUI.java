package Futoshiki;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class FutoschikiGUI extends JFrame {
	private static final String[] listaL = {"<",">","","","","","","","","","",""};
	private static final String[] listaV = {"v","^","","","","","","","","","",""};
	private JButton[][]celle;
	private static String difficoltà;
	private static int Diff;
	private Futoshiki gioco;
	private int NSOL=25;
	private static int Liv=0;;
	private static FutoschikiGUI f;
	GameTable gt;
	private JButton risolvi,risolviA;
	JMenuItem nuovaPartita,esci;
	private JFrame FinestraSol;
    private JPanel contentPane,p;
	private int valore;
	List<GameTable>lista;
    public FutoschikiGUI() {
    	Livello();
    	celle=new JButton[Liv+Liv-1][Liv+Liv-1];
    	super.setJMenuBar(createMenuBar());
		setTitle("Futoshiki");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 800, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(2, 2, 0, 0));
		contentPane.setLayout(new GridLayout(Liv+Liv, Liv+Liv,2, 2));
		setContentPane(contentPane);
		risolvi= new JButton("Verifica");
		risolviA= new JButton("Risolvi");
		risolvi.setBackground(Color.BLUE);
		risolviA.setBackground(Color.GREEN);
		//p=new JPanel();
		//contentPane.add(p);
		gt= new GameTable(Liv);
		costruiscitabella();
		//contentPane.add(p);
		contentPane.add(risolvi);
		contentPane.add(risolviA);
		
		risolvi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameTable gt1= new GameTable(Liv);
				costruisciGT(gt1);
				System.err.println("tabella del risolutore : \n"+gt1);
				
				gt=new GameTable(Liv);
				costruisciGTSoloRelazioni(gt);
				
				Futoshiki f=new Futoshiki(gt,NSOL);
				f.risolvi();
				lista=f.getSoluzioni();
				int rino=0;
				System.err.println("tabella prima del risolutore : \n"+gt);
				System.out.println("Verranno trovate soluzioni.");
				for(GameTable g:lista) System.out.println("solu:"+(rino++)+"\n"+g);
				boolean trovato=false;
				for(GameTable t:lista) {
					if(gt1.equals(t)) {trovato=true;}
				}
				lista.toString();
				if(trovato==true) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								JOptionPane.showMessageDialog(null, "Soluzione Esatta");
								JOptionPane.showMessageDialog(null,"Il gioco ha esattamente: "+lista.size()+" soluzioni!!");
								FinestraSol= new FinestraSol();
								FinestraSol.setVisible(true);
								FinestraSol.setTitle("Futoshiki");
								FinestraSol.setSize(900,700);
								FinestraSol.setLocation(100,20);
								FinestraSol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								FinestraSol.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				else {
					JOptionPane.showMessageDialog(null, "Soluzione Sbagliata");
				}
			}

		});
		
		risolviA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gt= new GameTable(Liv);
				NSol();
				costruisciGTSoloRelazioni(gt);
				Futoshiki f=new Futoshiki(gt,NSOL);
				f.risolvi();
				lista=f.getSoluzioni();
				try {
					gt=new GameTable(lista.get(0));
					inserisciGT(gt);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								FinestraSol= new FinestraSol();
								FinestraSol.setVisible(true);
								FinestraSol.setTitle("Futoshiki");
								FinestraSol.setSize(900,700);
								FinestraSol.setLocation(100,20);
								FinestraSol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								FinestraSol.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (RuntimeException e2) {
					JOptionPane.showMessageDialog(null, "Non esiste alcuna soluzione al sistema");
					System.exit(0);
				}
			}
			
			
		});
		
	}
    
	private void NSol() {
		boolean b=true;
		int ris=0;
		while(b) {
			try {
				String x=JOptionPane.showInputDialog("Inserisci il numero di soluzioni che vuoi avere: ");
				ris=Integer.parseInt(x);
				if(ris>0&&ris<50) {NSOL=ris;b=false;}
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null,"Inserisci un numero di sol acettabile che vada da 0 a 50");
			}
		}
	}
    

	private JMenuBar createMenuBar() {
    	JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Opzioni");
            nuovaPartita = new JMenuItem("Inizia Nuova Partita");
            esci = new JMenuItem("Esci");
            nuovaPartita.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e){
    				EventQueue.invokeLater(new Runnable() {
    					public void run() {
    						try {
    							f = new FutoschikiGUI();
    							f.setVisible(true);
    							f.setTitle("Futoshiki");
    							f.setSize(750,700);
    							f.setLocation(300,50);
    							f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    							f.setVisible(true);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
    					}
    				});
    			}
    		});
    		esci.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    	            System.exit(0);
    			}
    		});
    		
            menuFile.add(nuovaPartita);
            menuFile.addSeparator();
            menuFile.add(esci);
        menuBar.add(menuFile);
       return menuBar;
	}
	private void costruiscitabella() {
    	for(int i=0;i<celle.length;i++) {
			for(int j=0;j<celle[0].length;j++){
				celle[i][j]= new JButton();
				contentPane.add(celle[i][j]);
				if(j%2==1) {
					int r=new Random().nextInt(Diff);
					celle[i][j].setText(listaL[r]);
					contentPane.add(celle[i][j]);
					celle[i][j].setEnabled(false);
					if(listaL[r].equals("")) {
						celle[i][j].setVisible(false);
					}
				}
				JButton but=celle[i][j];
				but.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						valore=PossInserimenti ();
						but.setText(""+valore);
					}
				});
			}
			++i;
			if(i<celle.length-1&&i%2==1) {
				for(int j=0;j<celle[0].length;j++){
					celle[i][j]=new JButton();
					int r = 4 ;
					if(j%2!=1) {
						r=new Random().nextInt(Diff);
						celle[i][j]=new JButton();
						celle[i][j].setText(listaV[r]);
					}
					if(listaV[r].equals(""))celle[i][j].setVisible(false);
					celle[i][j].setEnabled(false);
					contentPane.add(celle[i][j]);
				}
			}
		}
	}
	protected int PossInserimenti() {
		boolean b=true;
		int ris=0;
    	while(b) {
	    	try {
	    		String c=JOptionPane.showInputDialog("Inserisci un valore tra "+"1 e "+Liv);
	    		ris=Integer.parseInt(c);
				if(ris>0&&ris<Liv+1)b=false;
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null,"Inserisci un valore intero tra 1 e "+Liv);
			}
		}
    	return ris;
		
	}
    
	
	private static void Livello() {
		boolean b=true;
		String ris="";
    	while(b) {
	    	try {
	    		ris=JOptionPane.showInputDialog("Inserisci livello di diffioltà tra : facile,medio,difficile; ");
	    		if(ris.toLowerCase().trim().equals("facile")) {Diff=12;b=false;CostruisciT();}
	    		else if(ris.toLowerCase().trim().equals("medio")){Diff=10;b=false;CostruisciT();}
	    		else if(ris.toLowerCase().trim().equals("difficile")){Diff=8;b=false;CostruisciT();}
			}catch(RuntimeException e) {
				JOptionPane.showMessageDialog(null,"Inserisci un il livello di diffioltà: facile,medio,difficile;");
			}
		}
	}
	private static void CostruisciT() {
    	boolean b=true;
		int ris;
		while(b) {
			try {
				JOptionPane p=new JOptionPane();
				String c=p.showInputDialog("inserisci il numero di celle al tuo gioco da 1 a 11:");
				ris=Integer.parseInt(c);
				if(ris>0&&ris<12) {Liv=ris;b=false;}
				if(c.equals("")) {Liv=5;b=false;}
			}catch(NumberFormatException e) {
				JOptionPane p=new JOptionPane();
				p.showMessageDialog(null,"Inserisci un valore esatto");
			}
		}
	}
	class FinestraSol extends JFrame {
		JButton prec,succ;
		private int k=1;
		private JPanel p;
		private JButton[][]soluzione=new JButton[Liv+Liv-1][Liv+Liv-1];
		public FinestraSol() {
			super.setJMenuBar(createMenuBar());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(500, 200, 800, 200);
			p= new JPanel();
			p.setBorder(new EmptyBorder(2, 2, 0, 0));
			p.setLayout(new GridLayout(Liv+Liv, Liv+Liv,2, 2));
			setContentPane(p);
			prec= new JButton("<");
			succ=new JButton(">");
			for(int i=0;i<soluzione.length;i++) {
				for(int j=0;j<soluzione[0].length;j++){
					soluzione[i][j]=new JButton();
					soluzione[i][j]=celle[i][j];
					p.add(soluzione[i][j]);
					celle[i][j].setEnabled(false);
				}
			}
			p.add(prec);
			p.add(succ);
			prec.setBackground(Color.GREEN);
			succ.setBackground(Color.GREEN);
			prec.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(k!=1)k--;
					gt=lista.get(k-1);
					inserisciGT(gt);
					setTitle("Soluzione"+k);
				}
			});
			succ.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(k!=NSOL+1&&k<lista.size())k++;
					gt=lista.get(k-1);
					inserisciGT(gt);
					setTitle("Soluzione"+k);
				}
			});
		}
	}
	private void costruisciGTSoloRelazioni(GameTable gt) {
		int i1,i2;
		i1=0;
		int limite=Liv+Liv-1;
		for(int i=0;i<limite;i++) {
			i2=0;
			for(int j=0;j<limite;j++) {
				if(j%2==1) {
					if(celle[i][j].getText().equals("<"))gt.setCellAt(i1, i2-1, new GameCell(gt.getCellValueAt(i1, i2-1), Relation.LESS,Relation.NONE));
					else if(celle[i][j].getText().equals(">"))gt.setCellAt(i1, i2-1, new GameCell(gt.getCellValueAt(i1, i2-1), Relation.GREATER,Relation.NONE));
					
				}
				if(j%2==0)i2++;
			}
			if(i%2==0)i1++;
			++i;
			if(i<celle.length-1&&i%2==1) {
				i2=0;
				for(int j=0;j<celle[0].length;j++){
					if(j%2!=1) {
						int x=i1-1;
						if(celle[i][j].getText().equals("^"))gt.setCellAt(x, i2, new GameCell(gt.getCellValueAt(x, i2),gt.getCellAt(x, i2).getEast(), Relation.LESS));
						else if(celle[i][j].getText().equals("v"))gt.setCellAt(x, i2, new GameCell(gt.getCellValueAt(x, i2),gt.getCellAt(x, i2).getEast(), Relation.GREATER));
						
					}
					if(j%2==0)i2++;
				}
			}
		}
	}
	private void costruisciGT(GameTable gt) {
		int i1,i2;
		i1=0;
		int limite=Liv+Liv-1;
		for(int i=0;i<limite;i++) {
			i2=0;
			for(int j=0;j<limite;j++) {
				if(i%2==0&&j%2==0) {
					String c=celle[i][j].getText();
					int v=0;
					if(c!="")v=Integer.parseInt(celle[i][j].getText());
					gt.setCellAt(i1, i2, new GameCell(v, Relation.NONE,Relation.NONE));
				}
				if(j%2==1) {
					if(celle[i][j].getText().equals("<"))gt.setCellAt(i1, i2-1, new GameCell(gt.getCellValueAt(i1, i2-1), Relation.LESS,Relation.NONE));
					else if(celle[i][j].getText().equals(">"))gt.setCellAt(i1, i2-1, new GameCell(gt.getCellValueAt(i1, i2-1), Relation.GREATER,Relation.NONE));
				}
				if(j%2==0)i2++;
			}
			if(i%2==0)i1++;
			++i;
			if(i<celle.length-1&&i%2==1) {
				i2=0;
				for(int j=0;j<celle[0].length;j++){
					if(j%2!=1) {
						int x=i1-1;
						if(celle[i][j].getText().equals("^"))gt.setCellAt(x, i2, new GameCell(gt.getCellValueAt(x, i2),gt.getCellAt(x, i2).getEast(), Relation.LESS));
						else if(celle[i][j].getText().equals("v"))gt.setCellAt(x, i2, new GameCell(gt.getCellValueAt(x, i2),gt.getCellAt(x, i2).getEast(), Relation.GREATER));
					}
					if(j%2==0)i2++;
				}
			}
		}
	}
	private void inserisciGT(GameTable gt) {
		int i1,i2;
		i1=0;
		for(int i=0;i<Liv+Liv-1;i++) {
			i2=0;
			for(int j=0;j<Liv+Liv-1;j++) {
				if(i%2==0&&j%2==0) {
					celle[i][j].setText(""+gt.getCellValueAt(i1, i2));
				}
				if(j%2==0)i2++;
			}
			if(i%2==0)i1++;
			++i;
		}
	}
	
	public static void main(String...args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					f = new FutoschikiGUI();
					f.setVisible(true);
					f.setTitle("Futoshiki");
					f.setSize(750,700);
					f.setLocation(300,50);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	
	
	
}

