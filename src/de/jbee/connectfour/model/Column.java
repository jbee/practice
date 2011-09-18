package de.jbee.connectfour.model;

public enum Column {

	A,
	B,
	C,
	D,
	E,
	F,
	G;

	static int size = values().length;

	public static final Column[] SEQ = { D, C, E, B, F, A, G };

	public static int size() {
		return size;
	}
}
