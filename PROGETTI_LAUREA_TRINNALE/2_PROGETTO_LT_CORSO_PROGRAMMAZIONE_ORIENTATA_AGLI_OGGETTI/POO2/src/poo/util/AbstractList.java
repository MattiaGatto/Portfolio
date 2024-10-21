package poo.util;
import java.util.Iterator;

public abstract class AbstractList<T> implements List<T> {
	public boolean equals(Object o) {
		if(!(o instanceof List)) return false;
		if (this==o)return true;
		List <T> l=(List)o;
		if(l.size()!=this.size())return false;
		Iterator <T>it1= this.iterator();
		Iterator <T>it2= l.iterator();
		while(it1.hasNext()) {
			if(!it1.next().equals(it2.next()))return false;
		}//while
		return true;
	}//equals
	public int hashCode() {
		final int M=43;
		int h=0;
		for(T o:this) {
			h=h*M+o.hashCode();
		}//for
		return h;
	}//hashCode
	public String toString() {
		Iterator <T>it= this.iterator();
		StringBuilder sb= new StringBuilder(500);
		sb.append("[ ");
		while(it.hasNext()){
			sb.append(it.next());
			if(it.hasNext())sb.append(',');
		}//while
		sb.append(" ]");
		return sb.toString();
	}//toString
}//AbstractList
