package de.jbee.p2challenge1;

public interface IQueue<T> {

	boolean isEmpty();

	int size();

	void add( T item ); //append item

	T top(); //return first item, assert if empty

	void remove(); //remove first item, assert if empty
}
