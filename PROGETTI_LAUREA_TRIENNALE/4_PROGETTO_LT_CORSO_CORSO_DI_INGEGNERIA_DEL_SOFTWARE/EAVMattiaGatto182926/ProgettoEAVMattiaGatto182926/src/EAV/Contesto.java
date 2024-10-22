package EAV;


import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Contesto {
	
	private Map<String,Integer> contesto;
	
	public Contesto() {
		this.contesto = new HashMap<>();
	}
	
	public Map<String,Integer> getContesto() {
		return contesto;
	}
	
	public void aggiungi(String context) {
		StringTokenizer st = new StringTokenizer(context," =");
		while(st.hasMoreTokens()) {
			String s = st.nextToken();
			if(!s.matches("[\\d]+")) {
				this.contesto.put(s,Integer.parseInt(st.nextToken()));
			}
		}
	}
	
}//Contesto