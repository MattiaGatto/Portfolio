package poo.util;

import java.util.Iterator;

public abstract class StackAstratto<T> implements Stack<T> {
	
	public abstract void push(T e);
	public abstract Iterator<T> iterator();
    
	public int size() {
		int c = 0;
		for (T e: this) c++;
		return c;
	} // size
	public void clear() {
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			it.next(); it.remove();
		}
	} // clear
	public boolean isEmpty() { return !iterator().hasNext(); }
	public boolean isFull() { return false; }
	public T pop() {
		Iterator<T> it = iterator();
		if (!it.hasNext()) throw new RuntimeException("Empty Stack!");
		T e = it.next(); it.remove();
		return e;
	} // pop
	public T top() {
		Iterator<T> it = iterator();
		if (!it.hasNext()) throw new RuntimeException("Empty Stack!");
		return it.next();
	}
	public int hashCode() {
		final int MOLT = 43; int h = 0;
		Iterator<T> it = iterator();
		while (it.hasNext())
			h = h * MOLT + it.next().hashCode();
		return h;
	} // hashCode
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Stack)) return false;
		if (o == this) return true;
		Stack<T> l = (Stack)o;
		if (size() != l.size()) return false;
		Iterator<T> it1 = iterator();
		Iterator<T> it2 = l.iterator();
		while (it1.hasNext())
			if (!it1.next().equals(it2.next())) return false;
		return true;
	} // equals
	public String toString() {
		StringBuilder sb = new StringBuilder(500);
		sb.append('[');
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	} // toString
} // StackAstratto
