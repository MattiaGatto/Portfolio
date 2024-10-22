package poo.util;

import java.util.*;

public class StackArray<T> extends StackAstratto<T> {
	private T[] array;
	private int size = 0;
	@SuppressWarnings("unchecked")
	public StackArray(int capacity) {
		if (capacity <= 0) throw new IllegalArgumentException();
		array = (T[])new Object[capacity];
	} // Costruttore
	public int size() { return size; }
	public void clear() {
		for (int i = 0; i < size; i++) array[i] = null;
		size = 0;
	} // clear
	public boolean isEmpty() { return size == 0; }
	public boolean isFull() { return size == array.length; }
	public void push(T e) {
		if (size == array.length) throw new RuntimeException("Full Stack!");
		array[size++] = e;
	} // push
	public T pop() {
		if (size == 0) throw new RuntimeException("Empty Stack!");
		T e = array[--size]; array[size] = null;
		return e;
	} // pop
	public T top() {
		if (size == 0) throw new RuntimeException("Empty Stack!");
		return array[size-1];
	} // top
	public Iterator<T> iterator() {
		return new StackIterator();
	} // iterator
	private class StackIterator implements Iterator<T> {
		private int cor = size; private boolean rimovibile = false;
		public boolean hasNext() {
			return cor > 0;
		} // hasNext
		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			rimovibile = true;
			return array[--cor];
		} // next
		public void remove() {
			if (!rimovibile) throw new IllegalStateException();
			rimovibile = false;
			for (int i = cor; i < size - 1; i++)
				array[i] = array[i + 1];
			array[--size] = null;
		} // remove
	} // StackIterator
} // StackArray
