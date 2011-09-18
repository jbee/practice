package de.jbee.connectfour.model;

public enum Row {

	_1,
	_2,
	_3,
	_4,
	_5,
	_6;

	static int size = values().length;

	@Override
	public String toString() {
		return name().substring( 1 );
	}

	public static int size() {
		return size;
	}

	public boolean isEven() {
		return ( ordinal() % 2 ) == 1; // since first row (0) is odd from point of view of a player
	}

	public boolean isOdd() {
		return !isEven();
	}
}
