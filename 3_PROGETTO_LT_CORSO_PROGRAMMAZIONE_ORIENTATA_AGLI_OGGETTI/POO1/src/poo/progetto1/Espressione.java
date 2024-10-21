import java.util.Comparator;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import poo.util.StackConcatenato;

public class Espressione {
	StringTokenizer st;
	Comparatore precedenza= new Comparatore();
	public Espressione(String s){
		st=new StringTokenizer(s,"()^%*/+-",true) ;
	}//Espressione
	public int valuta(){
		return valutaEspressione(st);
	}//valuta
	public int valutaEspressione(StringTokenizer st){
		StackConcatenato<Character> operatori= new StackConcatenato<>();
		StackConcatenato<Integer> operandi =new StackConcatenato<>();
		operandi.push(valutaOperando(st));
		while(st.hasMoreTokens()){
			char c= st.nextToken().charAt(0);
			if(c==')'){
				while(operatori.size()>0){
					char opc=operatori.pop();
					int o1= operandi.pop();
					int o2=operandi.pop();
					switch(opc){
						case '^':operandi.push((int)Math.pow(o2,o1));break;
						case '+':operandi.push(o2+o1);break;
						case '*':operandi.push(o2*o1);break;
						case '/':operandi.push(o2/o1);break;
						case '-':operandi.push(o2-o1);break;
						case '%':operandi.push(o2%o1);break;
						default :throw new RuntimeException("Espressione malformata");
					}//switch
				}//while
				return operandi.top();
				
			}//if
			while (operatori.size()>0&&precedenza.compare(c,operatori.top())<=0){
				int o1= operandi.pop();
				int o2=operandi.pop();
				switch(operatori.pop()){
					case '^':operandi.push((int)Math.pow(o2,o1));break;
					case '+':operandi.push(o2+o1);break;
					case '*':operandi.push(o2*o1);break;
					case '/':operandi.push(o2/o1);break;
					case '-':operandi.push(o2-o1);break;
					case '%':operandi.push(o2%o1);break;
					default :throw new RuntimeException("Espressione malformata");
				}//switch
			}//while
			operatori.push(c);operandi.push(valutaOperando(st));
			
		}//while
		while(operatori.size()>0){
			int o1= operandi.pop();
			int o2=operandi.pop();
			switch(operatori.pop()){
				case '^':operandi.push((int)Math.pow(o2,o1));break;
				case '+':operandi.push(o2+o1);break;
				case '*':operandi.push(o2*o1);break;
				case '/':operandi.push(o2/o1);break;
				case '-':operandi.push(o2-o1);break;
				case '%':operandi.push(o2%o1);break;
				default :throw new RuntimeException("Espressione malformata");
			
			}//switch
		}//while
		return operandi.top();
	}//valutaEspressione
	public int valutaOperando(StringTokenizer st){
		String c= st.nextToken();
		if(c.equals("("))return valutaEspressione(st);
		/**
		se volevamo gestire il caso di un "-" all' inizio dell' espressione:
	  		else if(c.equals("-"))return 0-valutaOperando(st);
		 **/
		return Integer.parseInt(c);
	}//valutaOperando
	
	private class Comparatore implements Comparator<Character>{
		
		public int compare(Character primo, Character secondo){
			
			switch(primo){
				case '^':return 1;
				case '*':if(secondo.equals('^'))return -1;if(secondo.equals('/')||secondo.equals('%'))return 0;return 1;
				case '/':if(secondo.equals('^'))return -1;if(secondo.equals('*')||secondo.equals('%'))return 0;return 1;
				case '%':if(secondo.equals('^'))return -1;if(secondo.equals('/')||secondo.equals('*'))return 0;return 1;
				case '+':if(secondo.equals('-'))return 0;return -1;
				case '-':if(secondo.equals('+'))return 0;return -1;
			
			}//switch
			return 0;
		}//compare
	}//Comparatore
	
	
	public static void main (String [] args){
		//Primo main con un programma valutatore interattivo di espressioni aritmetiche, fornite a fronte di un prompt �>> �
		/**
		System.out.println(">>Inserire un espressione da risolvere cosiderando i seguenti punti:");
		System.out.println(">> 1) Gli operatori utilizzabili sono: -, +, *, /, %, ^; ");
		System.out.println(">> 2) Si possono usare le parentesi tonde:'(' aperta e')'chiusa per delimitare una sotto-espressione; ");
		System.out.println(">> 3) Inserisci '.' per uscire;"); 
		Scanner sc= new Scanner(System.in);
		String s=null;
		for(;;){
			System.out.println(">>Inserire espressione:");
			System.out.print(">>");
			s=sc.nextLine();
			if(s.equals(".")) break;
			try{
				int risultato=new Espressione(s).valuta();
				System.out.println(">>Il risultato dell espressione:\n"+">>"+s+"   =   "+risultato);
			}catch(Exception e){
				System.out.println("Espressione malformata");
			}//catch
			
		}//for
		sc.close();
		System.exit(0);
		**/
		//un programma valutatore interattivo di espressioni aritmetiche, fornite a fronte di un interfaccia grafica con la 
		//gestione di casi evidenti di malformazioni in una espressione aritmetica mediante l�uso di espressioni regolari.
		
		JOptionPane.showMessageDialog(null,"Inserire un espressione da risolvere cosiderando i seguenti punti:\n1) Gli operatori utilizzabili sono: -, +, *, /, %, ^;\n2) Si possono usare le parentesi tonde:'(' aperta e')'chiusa per delimitare una sotto-espressione;\n3)Premi: VALUTA per sapere il risultato dell' espressione, mentre RESETTA per cancellare l'espressione; ");
		FinestraValuta fv= new FinestraValuta();
		fv.setVisible(true);
		
	}//main
	
}//Espressione
