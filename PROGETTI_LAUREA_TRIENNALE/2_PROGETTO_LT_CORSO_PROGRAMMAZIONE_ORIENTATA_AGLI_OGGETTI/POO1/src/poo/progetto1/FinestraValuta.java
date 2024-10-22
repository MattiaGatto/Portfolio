package poo.progetto1;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FinestraValuta extends JFrame implements ActionListener{
	   private JTextField espressione,risultato;
	   private JButton valuta,resetta;
	   private StringTokenizer st;
	   
	   public FinestraValuta(){
	      setTitle("Valutatore espressioni");
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      JPanel p=new JPanel();
	      p.add( new JLabel("ESPRESSIONE", JLabel.CENTER) );
	      p.add( espressione=new JTextField("",20) ); 
	      add(p, BorderLayout.NORTH);
	      JPanel l=new JPanel();
	      l.add( new JLabel("RISULTATO", JLabel.CENTER) );
	      l.add( risultato=new JTextField("",10) ); 
	      add(l, BorderLayout.CENTER);
	      JPanel q=new JPanel();
	      q.add( valuta= new JButton("VALUTA") );
	      q.add( resetta= new JButton("RESETTA") );
	      add( q, BorderLayout.SOUTH );
	      valuta.addActionListener( this );
	      resetta.addActionListener( this );
	      setLocation(300,300);
	      setSize(550,200);
	   }//FinestraValuta

	   public void actionPerformed(ActionEvent evt){//metodo callback
		 risultato.setText(null) ; 
		 String FORMATO="[\\(\\d+\\)[\\+\\-\\*\\/\\%\\^]]+";
		 String e=espressione.getText() ;
         st=new StringTokenizer(e,"()",true);
         int c=0;
         while(st.hasMoreTokens()){
 			char s= st.nextToken().charAt(0);
 			if(s==('('))c++;
 			if(s==(')'))c--;
 		 }//while
         if( evt.getSource()==valuta){
        	 if(e.matches(FORMATO)&&c==0){
    			 try {
	    			 double ris=new Espressione(e).valuta();
	    			 risultato.setText( String.format("%1.0f",ris) );
    			 }catch(RuntimeException s) {
    				 JOptionPane.showMessageDialog(null,"Espressione malformata");
    				 /**
    	        	  * se volevamo resettare il campo dell' espressione quando l' espressione � malformata:
    	        	  *  	 espressione.setText(null);
    	        	 */
    			 }//catch
    		 }
    		 else {
    			 JOptionPane.showMessageDialog(null,"Espressione malformata");
    		 }//else
    	 }//if
    	 if( evt.getSource()==resetta ){
    		 espressione.setText(null);
    	 }//if
    }//actionPerformed   
}//FinestraValuta

	