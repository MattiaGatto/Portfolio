package poo.util;

public interface Stack<T> extends Iterable<T> {
	int size();
	void clear();
	void push(T e);
	T pop();
	T top();
	boolean isEmpty();
	boolean isFull();
} // Stack
