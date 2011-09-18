package de.jbee.connectfour.model;

public final class Position {

	public final Column column;
	public final Row row;

	public Position( Column col, Row row ) {
		this.column = col;
		this.row = row;
	}

	@Override
	public String toString() {
		return column.toString() + row.toString();
	}
}
