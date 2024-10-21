package poo.progetto2;

import java.util.ListIterator;

import poo.util.*;

public class Test {
	public static void main(String[] args){
		List<Integer> ll=new LinkedList<>();
		System.out.println("Test *1* addLast di <9,2,5>");
		ll.add(9); ll.addLast(2); ll.addLast(5); 
		System.out.println(ll);
		System.out.println("Test *2* addFirst di <7,8,4>");
		ll.addFirst(7); ll.addFirst(8); ll.addFirst(4);
		System.out.println(ll);

		System.out.println("Test *3* tre removeFirst");
		ll.removeFirst(); ll.removeFirst(); ll.removeFirst();
		System.out.println(ll);
		
		System.out.println("Test *4* sorting");
		ll.sort((x,y)->{ return x-y; } );
		
		System.out.println(ll);
		
		System.out.println("Test *5* add di <-1,-2> in testa - su listIterator()");
		ListIterator<Integer> lit=ll.listIterator();
		lit.add(-1); lit.add(-2);
		System.out.println(ll);
		
		System.out.println("Test *6* ripresa iteratore forward dopo cascata di add in testa");
		while( lit.hasNext() ){
			System.out.print( lit.next()+" ");
		}
		System.out.println();
		System.out.println("Test *7* ripresa iteratore backward dopo forward");
		while( lit.hasPrevious() ){
			System.out.print( lit.previous()+" ");
		}
		System.out.println("Test *8* size");
		System.out.println("size="+ll.size());
		
		System.out.println("Test *9* add di <30,40> in coda - su listIterator(size)");
		lit=ll.listIterator(ll.size());
		lit.add(30); lit.add(40);
		System.out.println(ll);

		System.out.println("Test *10* add di <100,500> partire dall'indice 3 - su listIterator(3)");
		lit=ll.listIterator(3);
		lit.add(100); lit.add(500);
		try{
			lit.remove();
		}catch( IllegalStateException e ){
			System.out.println("Errore: remove proibita dopo una add");
		}
		System.out.println(ll);
		System.out.println("Test *11* rimozione elemento");
		lit=ll.listIterator();
		while( lit.hasNext() ){
			int x=lit.next();
			if( x==500 ) lit.remove();
		}
		System.out.println(ll);

		System.out.println("Test *12* re-sorting");
		ll.sort((x,y)->{ return x-y; } );
		System.out.println( ll );
		
		System.out.println("Test *13* insertion sort di <57,23,12> - forward");
		int []a={57,23,12};
		for( int x: a){
			boolean flag=false;
			ListIterator<Integer> it=ll.listIterator();
			while( it.hasNext() && !flag ){
				int y=it.next();
				if( y>=x ){			
					it.previous();
					it.add(x);
					flag=true;
				}
			}
			if( !flag ) it.add(x);
		}
		System.out.println(ll);
		
		System.out.println("Test *14* rimozione di <57,12,23>");
		ListIterator<Integer> li=ll.listIterator();
		while( li.hasNext() ){ 
			int u=li.next(); 
			if( u==12 || u==23 || u==57 ){
				li.remove(); 
				try{
					li.remove();
				}catch( IllegalStateException e ){
					System.out.println("Errore: remove proibita dopo remove");
				}
			}
		}
		System.out.println(ll);
		System.out.println("Test *15* insertion sort di <57,12,23> - backward");
		for( int x: a){
			boolean flag=false;
			ListIterator<Integer> it=ll.listIterator(ll.size());
			while( it.hasPrevious() && !flag ){
				int y=it.previous();
				if( y<=x ){
					it.next();
					it.add(x);
					flag=true;
				}
			}
			if( !flag ) it.add(x);
		}
		System.out.println(ll);		
		System.out.println("Test *16* clear e sort subito dopo");
		ll.clear();
		ll.sort((x,y)->{return x-y;});
		System.out.println(ll);
		System.out.println("Test *17* sort");
		List<Integer> lin=new LinkedList<>();
		lin.addFirst(9); lin.addFirst(2); lin.addFirst(-3); lin.addFirst(4); lin.addFirst(1);
		System.out.println("Lista lin iniziale="+lin);
		lin.sort((x,y)->{return x-y;});
		System.out.println("Lista lin sorted="+lin);
		System.out.println("Bye.");
	}//main
}//Test

