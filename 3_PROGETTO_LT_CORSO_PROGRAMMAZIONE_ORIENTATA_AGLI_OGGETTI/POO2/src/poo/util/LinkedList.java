import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class LinkedList<T> extends AbstractList<T> {
    
	public enum Movement{BACKWARD,FORWARD,UNKNOWN};
	
	public int size = 0;
	private Nodo<T> testa = null, coda = null;
	//nodo iniziale e finale della LinkedList
	
	private static class Nodo<E>{
		E info; //informazione
		Nodo<E> prior, next; //nodi adiacenti
	}
	
	public int size(){
		return size;
	}
	
	public void add(T e){
		this.addLast(e);
	}//add
	
	public void remove(T e){
		if(!this.contains(e)) 
			return;
		ListIteratore l = new ListIteratore(0);
		boolean flag = false;
		while(!flag){
			T x = l.next();//portiamo l'iteratore avanti
			if(x.equals(e)){
				l.remove();//rimozione
				flag = true;//trovato
		    }//if
		}//while
	}//remove

	public void addFirst(T e) {
		Nodo<T> n = new Nodo<>(); n.info = e;
		n.next = testa;
		n.prior = null;
		if(isEmpty()) //se la lista e' vuota
			coda = n; //n sara' anche il last
		else{
			testa.prior = n;
		}//else
		testa = n; //first puntera' ad n
		size++;
	} // addFirst

	
	public void addLast(T e) {
		Nodo<T> n = new Nodo<>(); n.info = e;
		n.next = null;
		n.prior = coda;
		if(isEmpty())//se la lista e' vuota
			testa = n;//n sara' anche il last
		else{
			coda.next = n;
		}//else
		coda = n; // last puntera' a n
		size++;
	}//addLast

	
	public T removeFirst() {
		if(testa == null) throw new NoSuchElementException();
		T x = testa.info;//elemtno da rimuovere
		if(testa == coda){ //lista fatta da un solo nodo
			coda = null; testa = null;
		}//if
		else{//lista fatta da piu di 1 nodo.
			testa = testa.next;
			testa.prior = null;
		}//else
		size--;
		return x;
	}//removeFirst

	
	public T removeLast() {
		if(coda == null) throw new NoSuchElementException();
		T x = coda.info; //elemento da rimuovere
		if(coda == testa){//lista fatta da un solo nodo
			coda = null; testa = null;
		}//if
		else{//lista fatta da piu di 1 nodo
			coda = coda.prior;
			coda.next = null;;
		}//else
		size--;
		return x;
	}//removeLast
	
	@Override
	public T getFirst() {
		if(isEmpty()) throw new NoSuchElementException();
		return testa.info;
	}//getFirst

	@Override
	public T getLast() {
		if(isEmpty()) throw new NoSuchElementException();
		return coda.info;
	}//getLast
	
	
	public ListIterator<T> listIterator(){
		return new ListIteratore(0);
	} //Iteratore della LinkedList
	
	
	
	public Iterator<T> iterator(){
		return new ListIteratore(0);
	}//Iteratore Semplice
	
	
	public ListIterator<T> listIterator(int pos){
		return new ListIteratore(pos);
	}//Iteratore della LinkedList partendo da pos
	
	private class ListIteratore implements ListIterator<T>{
		private Nodo<T> previous = null,corrente = testa;
		//il cursore si trova tra 2 nodi
		
		private Movement movimento = Movement.UNKNOWN;
		//descrive l'ultimo movimento effettuato
		//(se e' forward,si e' spostato in avanti)
		//(se e' backward,si e' spostato indietro)
		
		public ListIteratore(int pos){
			if(pos < 0 || pos > size()) throw new IllegalArgumentException("Posizione illegale");
			int i = 0;
			while(i < pos){
				if(pos == size()-1){
					previous = coda;
					corrente = null;
				}//if
				previous = corrente;
				corrente = corrente.next;
				i++;
			}//while
		}//costruttore 
		
		public boolean hasNext(){
			return corrente != null;
		}//hasNext
		
		public T next(){
			if(!hasNext()) throw new NoSuchElementException();
			previous = corrente;
			corrente = corrente.next;
			movimento = Movement.FORWARD;
			return previous.info;
		}//next
		
		public boolean hasPrevious(){
			return previous != null;
		}//hasPrevious
		
		public T previous(){
			if(!(hasPrevious())) throw new NoSuchElementException();
			corrente = previous;
			previous = previous.prior;
			movimento = Movement.BACKWARD;
			return corrente.info;
		}//previous

		public void add(T e) {
			Nodo<T> a = new Nodo<>(); 
			a.info = e;
			if(testa == null && coda == null){//lista vuota
				testa = a;//unico nodo
				coda = a;//unico nodo
				a.prior = null;
				a.next = null;
			}//if
			else if(previous == null){//cursore a 0
				testa.prior = a;
				a.prior = null;
				a.next = testa;
				testa = a;
			}//else if
			else if(corrente == null){//cursore a size
				a.next = null;
				a.prior = coda;
				coda.next = a;
				coda = a;
			}//else if
			else{//cursore con una lista con piu di 2 elementi
				a.next = corrente;
				a.prior = previous;
				corrente.prior = a;
				previous.next = a;
			}//else
			
			previous = a;
			size++;
			
		}//add
		
		public int nextIndex() {
			int i = 0;
			Nodo<T> prossimo = testa;
			boolean flag = false;
			while(!flag && prossimo!=null){
				if(prossimo.equals(corrente)){
					flag = true;//trovato
					return i;
				}//if
				else{
					i++;
					prossimo = prossimo.next;
				}//else
		   }//while
		   return -1;
		}//nextIndex

		
		public int previousIndex() {
			int i = 0;
			Nodo<T> prossimo = testa;
			boolean flag = false;
			while(!flag && prossimo!=null){
				if(prossimo.equals(previous)){
					flag = true;
					return i;
				}//if
				else{
					i++;
					prossimo = prossimo.next;
				}//else
		   }//while
		   return -1;
		}//previousIndex

		public void remove() {
		    if(movimento == Movement.UNKNOWN) throw new IllegalStateException();
			Nodo<T> n = null;
			if(movimento == Movement.BACKWARD)
				n = corrente;
			else
				n = previous;
			//in n abbiamo il nodo da rimuovere
			if(n == testa){
				testa = testa.next;
				if(testa == null) 
					coda = null;
				else
					testa.prior = null;
			}//if
			else if(n == coda){
				coda = coda.prior;
				coda.next=null;
			}
			else{
				n.prior.next = n.next;
				n.next.prior = n.prior;
			}//else
			if(movimento == Movement.BACKWARD)
				corrente = n.next;
			else
				previous = n.prior;
			movimento = Movement.UNKNOWN;
			size--;
		}//remove
		//remove

		
		public void set(T e) {
			if(movimento == Movement.UNKNOWN)//nessun spostamento
				throw new IllegalStateException();
			if(movimento == Movement.BACKWARD)
				corrente.info = e;
			else
				previous.info = e;
		}//set
		
		
		
		
	}

	public void sort(Comparator<T> c) {
		Nodo<T> a = testa;
		int j = size - 1;
		while(j > 0){
			int i = 0;
			for(;i<j; i++){
	            if(c.compare(a.info, a.next.info)>0){
	                 T temp = a.info; 
	                 a.info = a.next.info;
	                 a.next.info = temp;
	            }//if
	            a = a.next;
	        }//for
		  a = testa;	
	      j--; 
	   }//while
		
   }//sort
}//LinkedList
