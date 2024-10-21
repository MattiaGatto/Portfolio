package poo.util;



import java.util.*;

public class StackConcatenato<T> extends StackAstratto<T> {
	private static class Nodo<E> {
		E info;
		Nodo<E> next;
	} // Nodo
	private Nodo<T> testa = null;
	private int size = 0;
	public int size() { return size; }
	public void clear() { testa = null; size = 0; }
	public boolean isEmpty() { return testa == null; }
	public void push(T e) {
		Nodo<T> nuovo = new Nodo<T>();
		nuovo.info = e; nuovo.next = testa;
		testa = nuovo; size++;
	} // push
	public T pop() {
		if (testa == null) throw new RuntimeException("Empty Stack");
		T e = testa.info; testa = testa.next; size--;
		return e;
	} // pop
	public Iterator<T> iterator() {
		return new StackIterator();
	} // iterator
	private class StackIterator implements Iterator<T> {
		private Nodo<T> cor = null, pre = null;
		public boolean hasNext() {
			if (cor == null) return testa != null;
			return cor.next != null;
		} // hasNext
		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			if (cor == null) cor = testa;
			else { pre = cor; cor = cor.next; }
			return cor.info;
		} // next
		public void remove() {
			if (pre == cor) throw new IllegalStateException();
			if (cor == testa) testa = testa.next;
			else pre.next = cor.next;
			size--; cor = pre;
		} // remove
	} // StackIterator
} // StackConcatenato

