import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public interface List<T> extends Iterable<T>{
	default int size() {
		int c=0;
		Iterator<T> it= iterator(); 
		while (it.hasNext())c++;
		return c;
	}//size
	default void clear() {
		Iterator<T> it= iterator(); 
		while (it.hasNext()) {it.next();it.remove();}
	}//clear
	default boolean contains( T e ) {
		Iterator<T> it= iterator(); 
		while (it.hasNext())
			if(it.next().equals(e))return true;
		return false;
	}//contains
	default boolean isEmpty() {
		return !iterator().hasNext();
	}//isEmpty
	//void add(T e);
	default void add(T e) {
		ListIterator<T> lit= listIterator();
		while(lit.hasNext()) lit.next();
		lit.add(e);
	}
	
	default void remove( T e ) {
		Iterator<T> it= iterator();
		if(!it.hasNext()) throw new NoSuchElementException();
		while(it.hasNext()) {
			if(it.next().equals(e)) {
				it.remove();
				break;
			}//if
		}//while
	}//remove
	ListIterator<T> listIterator();
	ListIterator<T> listIterator( int pos );
	//void addFirst(T e);
	default void addFirst( T e ) {
		ListIterator<T> lit=listIterator();
		lit.add(e);
	}//addFirst
	//void addLast(T e);
	default void addLast(T e) {
		ListIterator<T> lit= listIterator();
		while(lit.hasNext()) lit.next();
		lit.add(e);
		
		
	}//addLast
	
	default T removeFirst() {
		if(isEmpty()) throw new NoSuchElementException("La lista � vuota");
		Iterator<T> it= iterator();
		T l=it.next();
		it.remove();
		return l;
		
	}//removeFirst
	default T removeLast() {
		if(isEmpty()) throw new NoSuchElementException();
		Iterator<T> it= iterator();
		T l=null;
		while (it.hasNext())l=it.next();
		it.remove();
		return l;		
	}//removeLast
	default T getFirst() {
		if(isEmpty()) throw new NoSuchElementException();
		Iterator<T> it= iterator();
		T l=it.next();
		return l;
	}//getFirst
	default T getLast() {
		if(isEmpty()) throw new NoSuchElementException();
		Iterator<T> it= iterator();
		T l=null;
		while (it.hasNext())l=it.next();
		return l;
	}//getLast
	void sort( Comparator<T> c );
}//List