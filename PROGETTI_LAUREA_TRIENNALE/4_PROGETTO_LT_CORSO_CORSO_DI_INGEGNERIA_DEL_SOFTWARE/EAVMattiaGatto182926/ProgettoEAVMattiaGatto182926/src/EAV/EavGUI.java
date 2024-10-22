package EAV;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EavGUI extends JFrame {
	private JTextField expr, result;
	JMenuItem context,rimpiazza,esci;
	private JButton salva,caricaExpr,valute;
	private JFrame jf = new FinestraContesto();
	private JFrame j = new FinestraRimpiazza();
	private Contesto c = new Contesto();
	private Parser parser;
	
	public EavGUI() {
		super.setJMenuBar(createMenuBar());
		
		JPanel p = new JPanel();
		JPanel q = new JPanel();
		p.add(new JLabel("Inserisci Espressione:", JLabel.RIGHT));
		p.add(expr = new JTextField("",15));
		p.add(new JLabel("Risultato:", JLabel.RIGHT));
		p.add(result = new JTextField("",15));
		result.setEditable(false);
		q.add(valute = new JButton("Valuta"));
		q.add(salva = new JButton("Salva"));
		q.add(caricaExpr = new JButton("Carica Expr"));
		this.setResizable(false);
		
		valute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String espressione = expr.getText();
					parser = new Parser(espressione,c);
					Integer ris = parser.getE().interpreta(espressione);
					result.setText(ris.toString());
				}catch(RuntimeException exc) {
					JOptionPane.showMessageDialog (null,"Dati inseriti errati");
				}
			}
		});
		
		context.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(true);
			}
		});
		
		rimpiazza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.setVisible(true);
			}
		});
		
		salva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					salvaExpr("expr");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		caricaExpr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					caricaExpr("expr");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		esci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            System.exit(0);
			}
		});
		
		add(p, BorderLayout.NORTH);
		add(q, BorderLayout.CENTER);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Opzioni");
            context = new JMenuItem("Contesto");
            rimpiazza = new JMenuItem("Rimpiazza Costante");
            esci = new JMenuItem("Esci");
            menuFile.add(context);
            menuFile.add(rimpiazza);//popup pull-right
            menuFile.addSeparator();
            menuFile.add(esci);
        menuBar.add(menuFile);
       return menuBar;
	}
	
	

	private void salvaExpr(String nomeFile) throws IOException {
    	PrintWriter pw=new PrintWriter( new FileWriter(nomeFile));
    	Boolean b=true;
    	if(expr.getText().trim().equals(""))b=false;
		if(b){
			pw.println(expr.getText()+"="+result.getText());
			pw.close();		
		}
		else{
			JOptionPane.showMessageDialog(null, "Non hai inserito alcuna espressione");
		}
	}
	
	private void caricaExpr(String nomeFile) throws IOException {
    	BufferedReader br=new BufferedReader( new FileReader(nomeFile));
		for(;;) {
			String s = br.readLine();
			if(s == null) {
				br.close();
				break;
			}
			JOptionPane x=new JOptionPane();
			JButton si= new JButton();
			si.setText("Ok");
			x.add(si);
			x.showMessageDialog(null,"Vuoi caricare la seguente espressione?   "+s);
			si.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			StringTokenizer st = new StringTokenizer(s,"=");
			expr.setText(st.nextToken());
			result.setText(st.hasMoreElements()?st.nextToken():"");
			
		}
	}
	
	
	
	
	class FinestraContesto extends JFrame {
		
		private JTextField contesto;
		private JButton chiudi;
		private JButton salvaContext;
		private JButton caricaContext,resettaContext;
		
		public FinestraContesto() {
			setTitle("Contesto");
			setSize(700,100);
			setLocation(50,200);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			JPanel p = new JPanel();
			p.add(new JLabel("Contesto:", JLabel.RIGHT));
			p.add(contesto = new JTextField("",12));
			p.add(chiudi = new JButton("OK"));
			p.add(salvaContext = new JButton("Salva Context"));
			p.add(caricaContext = new JButton("Carica Context"));
			p.add(resettaContext = new JButton("Resetta Context"));
			this.add(p);
			
			chiudi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean b=true;
					StringTokenizer st= new StringTokenizer(contesto.getText()," ");
					while(st.hasMoreTokens()) {
						if(!st.nextToken().matches("[A-Za-z]+=[0-9]+"))b=false;
					}
					if(b) {
						if(contesto.getText().isEmpty()) {
							c.getContesto().clear();
						}
						else {
							c.aggiungi(contesto.getText());
						}
						jf.setVisible(false);
					}else JOptionPane.showMessageDialog(null, "Soluzione errat inserisci un contesto del tipo:x=3 y=5 ecc");
				}
			});
			resettaContext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contesto.setText(null);
					if(contesto.getText().isEmpty()) {
						c.getContesto().clear();
					}
					try {
						salvaContext("context");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					jf.setVisible(false);
				}
			});
			
			salvaContext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						boolean b=true;
						StringTokenizer st= new StringTokenizer(contesto.getText()," ");
						while(st.hasMoreTokens()) {
							if(!st.nextToken().matches("[A-Za-z]+=[0-9]+"))b=false;
						}
						if(b) {
							if(contesto.getText().isEmpty()) {
								c.getContesto().clear();
							}else {
								c.aggiungi(contesto.getText());
							}
							salvaContext("context");
						}
						else JOptionPane.showMessageDialog(null, "Soluzione errat inserisci un contesto del tipo:x=3 y=5 ecc");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			caricaContext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						caricaContext("context");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			
			this.setResizable(false);
		}
		
		private void salvaContext(String nomeFile) throws IOException {
			PrintWriter pw=new PrintWriter( new FileWriter(nomeFile));
			Set<String> variabili = c.getContesto().keySet();
			for(String var: variabili) {
				pw.println(var+": " + c.getContesto().get(var));
			}
			pw.close();	
		}
		
		private void caricaContext(String nomeFile) throws IOException {
	    	BufferedReader br=new BufferedReader( new FileReader(nomeFile));
	    	String v = "";
			for(;;) {
				String s = br.readLine();
				if(s == null) {
					br.close(); break;
				}
				StringTokenizer st = new StringTokenizer(s," :");
				v += st.nextToken()+"="+st.nextToken()+" " ;

			}
			JOptionPane x=new JOptionPane();
			JButton si= new JButton();
			si.setText("Ok");
			x.add(si);
			x.showMessageDialog(null,"Vuoi caricare la seguente espressione?   "+v+"\n");
			si.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			contesto.setText(v);
		}
	
	}//FinestraContesto
	
	class FinestraRimpiazza extends JFrame {
		
		private JButton ok;
		private JTextField variabile;
		private JTextField espressione;
		
		public FinestraRimpiazza() {
			setSize(700,100);
			setLocation(50,200);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			JPanel p = new JPanel();
			JPanel q = new JPanel();
			p.add(new JLabel("Variabile:", JLabel.RIGHT));
			p.add(variabile = new JTextField("",12));
			p.add(new JLabel("Expr:", JLabel.RIGHT));
			p.add(espressione = new JTextField("",12));
			q.add(ok = new JButton("OK"));
			this.setResizable(false);
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String var = variabile.getText();
					String ex = espressione.getText();
					expr.setText(expr.getText().replaceAll(var, ex));
					variabile.setText("");
					espressione.setText("");
					j.dispose();
				}
			});
			this.add(p,BorderLayout.NORTH);
			this.add(q,BorderLayout.SOUTH);
		}
		
		
	}
	
	public static void main(String...args) {
		JFrame f = new EavGUI();
		f.setTitle("EAV Valutatore di espressioni aritmetiche intere con contesto");
		f.setSize(1000,200);
		f.setLocation(100,250);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
}//EavGUI