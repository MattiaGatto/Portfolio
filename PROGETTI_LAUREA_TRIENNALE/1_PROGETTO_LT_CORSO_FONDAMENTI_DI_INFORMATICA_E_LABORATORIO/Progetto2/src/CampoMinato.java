import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CampoMinato extends JFrame {
	private JButton[][]bottoni=new JButton[8][8];
	private Gioco gioco;
    private int Nuovo = 200;
    private String Difficolta;
	private JPanel contentPane;
	private final ImageIcon img = new ImageIcon("mina.png");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CampoMinato frame = new CampoMinato();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CampoMinato() {
		
		Difficolta=JOptionPane.showInputDialog("Scegli la difficolta tra: facile,media, difficile e personalizzata");
		
		while (!Difficolta.equals("facile")&&!Difficolta.equals("media")&&!Difficolta.equals("difficile")&&!Difficolta.equals("personalizzata")){
			resetta();
		}
		switch(Difficolta){
		case "facile":gioco=new Gioco(Gioco.Difficolta.FACILE);
		break;
		case "media":gioco=new Gioco(Gioco.Difficolta.MEDIA);
		break;
		case "difficile":gioco=new Gioco(Gioco.Difficolta.DIFFICILE);
		break;
		case "personalizzata":gioco=new Gioco(Gioco.Difficolta.PERSONALIZZATA);
		break;
		}
		setTitle("CAMPO MINATO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(8, 8, 0, 0));
		for(int i=0;i<bottoni.length;i++)
			for(int j=0;j<bottoni[0].length;j++){
				bottoni[i][j] = new JButton("");
				contentPane.add(bottoni[i][j]);
				bottoni[i][j].addActionListener(new PressioneBottone());
		}	
	}
	public void resetta(){
		
		Difficolta=JOptionPane.showInputDialog("Hai sbaliato a inserire il livello di Difficolta; Scegli la Difficolta tra: facile,media, difficile e personalizzata");
		
	}
	
	
	private class PressioneBottone implements ActionListener{
		public void aggiorna(int r,int c){
			Casella[][]campo=gioco.getCampo();
					if(gioco.scopriCasella(campo[r][c])){
						switch(campo[r][c].getValore()){
						case -1:
						
						bottoni[r][c].setIcon(img);
						break;
						case 0:
							bottoni[r][c].setText("0");
							bottoni[r][c].setForeground(Color.GREEN);
							for(int i=-1;i<2;i++)
								for(int j=-1;j<2;j++){
									if(r+i>=0&&r+i<campo.length&&c+j>=0&&c+j<campo[0].length)
									aggiorna(r+i,c+j);	
								}
							break;
						case 1:bottoni[r][c].setText("1");bottoni[r][c].setForeground(Color.BLUE);break;
						case 2:bottoni[r][c].setText("2");bottoni[r][c].setForeground(Color.ORANGE);break;
						case 3:bottoni[r][c].setText("3");bottoni[r][c].setForeground(Color.MAGENTA);break;
						case 4:bottoni[r][c].setText("4");bottoni[r][c].setForeground(Color.CYAN);break;
						case 5:bottoni[r][c].setText("5");bottoni[r][c].setForeground(Color.DARK_GRAY);break;
						case 6:bottoni[r][c].setText("6");bottoni[r][c].setForeground(Color.BLACK);break;
						case 7:bottoni[r][c].setText("7");bottoni[r][c].setForeground(Color.YELLOW);break;
						case 8:bottoni[r][c].setText("8");bottoni[r][c].setForeground(Color.PINK);break;
						}
					}
		}
		public void GiocaDiNuovo(String s){
			
			Nuovo=JOptionPane.showConfirmDialog(null, s,"",JOptionPane.YES_NO_OPTION);
			if(Nuovo==JOptionPane.YES_OPTION){
				nuovoGioco();
				resettaBottoni();
			}	
			else if(Nuovo==JOptionPane.NO_OPTION)
				System.exit(0);
		}
		public void actionPerformed(ActionEvent e){
			JButton b=(JButton) e.getSource();
			for(int i=0;i<bottoni.length;i++)
				for(int j=0;j<bottoni[0].length;j++){
					if(bottoni[i][j]==b){
						aggiorna(i,j);
						if (gioco.vittoria())
							GiocaDiNuovo("Complimenti,hai vinto,vuoi rigiocare?");
						
						else if(gioco.sconfitta())
							GiocaDiNuovo("Peccato,hai perso,vuoi rigiocare?");
						
					}
				}
		}
		private void nuovoGioco() {
			Difficolta=JOptionPane.showInputDialog("Scegli lil livello di Difficolta:");
			while (!Difficolta.equals("facile")&&!Difficolta.equals("media")&&!Difficolta.equals("difficile")&&!Difficolta.equals("personalizzata")){
				resetta();
			}
			switch(Difficolta){
			case "facile":gioco=new Gioco(Gioco.Difficolta.FACILE);
			break;
			case "media":gioco=new Gioco(Gioco.Difficolta.MEDIA);
			break;
			case "difficile":gioco=new Gioco(Gioco.Difficolta.DIFFICILE);
			break;
			case "personalizzata":gioco=new Gioco(Gioco.Difficolta.PERSONALIZZATA);
			break;
			}
			
		}
		public void resettaBottoni() {
			for(int r=0;r<bottoni.length;r++)
				for(int c = 0; c < bottoni.length; c++) {
					
					bottoni[r][c].setText(" ");
					bottoni[r][c].setForeground(null);
					bottoni[r][c].setIcon(null);
				}
		}
	
		
	}
}
