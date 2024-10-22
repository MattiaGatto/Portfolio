package testing;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.StringTokenizer;

import org.junit.Test;

import EAV.Contesto;
import EAV.Parser;

public class Testing {
	@Test
	public void prova1() {
		Contesto c = new Contesto();
		String espressione = "3+3+3+(6/2)*2";
		Parser p = new Parser(espressione,c);
		Integer ris = p.getE().interpreta(espressione);
		assertEquals("15", ris.toString());
	}
	
	@Test(expected=Error.class)
	public void prova2() {
		Contesto c = new Contesto();
		String espressione = "3+3+3+(6/2)*2";
		Parser p = new Parser(espressione,c);
		Integer ris = p.getE().interpreta(espressione);
		assertEquals("22", ris.toString());
	}
	
	@Test(expected=RuntimeException.class)
	public void prova3() {
		Contesto c = new Contesto();
		String espressione = "3+3+3+(6/2)*2+x";
		Parser p = new Parser(espressione,c);
		Integer ris = p.getE().interpreta(espressione);
		assertEquals("22", ris.toString());
	}
	
	@Test
	public void prova4() {
		Contesto c = new Contesto();
		c.aggiungi("a=5");
		String espressione = "a*2";
		Parser p = new Parser(espressione,c);
		Integer ris = p.getE().interpreta(espressione);
		assertEquals("10", ris.toString());
	}
	
	@Test(expected=Error.class)
	public void prova5() {
		Contesto c = new Contesto();
		c.aggiungi("a=5");
		c.aggiungi("a=3");
		String espressione = "a*2";
		Parser p = new Parser(espressione,c);
		Integer ris = p.getE().interpreta(espressione);
		assertEquals("10", ris.toString());
	}
	@Test
	public void prova6() throws IOException {
		
		Contesto c = new Contesto();
		c.aggiungi("a=5");
		PrintWriter pw=new PrintWriter( new FileWriter("context"));
		Set<String> variabili = c.getContesto().keySet();
		for(String var: variabili) {
			pw.println(var+": " + c.getContesto().get(var));
		}
		pw.close();
	}
	@Test
	public void prova7() throws IOException {
		Contesto c = new Contesto();
		c.aggiungi("a=5");
		BufferedReader br=new BufferedReader( new FileReader("Context"));
    	String v = "";
		for(;;) {
			String s = br.readLine();
			if(s == null) {
				br.close(); break;
			}
			StringTokenizer st = new StringTokenizer(s," :");
			v += st.nextToken()+"="+st.nextToken()+" " ;
			
		}
		assertEquals("a=5 ", v);
	}
	
	@Test
	public void prova8() throws IOException {
		
		PrintWriter pw=new PrintWriter( new FileWriter("expr"));
		pw.println("2+2-6+9=7");
		pw.close();
	}
	@Test
	public void prova9() throws IOException {
		PrintWriter pw=new PrintWriter( new FileWriter("expr"));
		pw.println("2+2-6+9=7");
		pw.close();
		BufferedReader br=new BufferedReader( new FileReader("expr"));
		for(;;) {
			String s = br.readLine();
			if(s == null) {
				br.close();
				break;
			}
			assertEquals("2+2-6+9=7",s);
		}
	}
	
	

}
