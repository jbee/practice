package de.jbee.connectfour.model.ai;

import java.util.EnumSet;

import de.jbee.connectfour.model.Column;

public class Move {

	private Column move;
	private EnumSet<Column> avoidedColumns = EnumSet.noneOf( Column.class );

	public void makeIn( Column column ) {
		move = column;
	}

	public boolean isAvoided( Column column ) {
		return avoidedColumns.contains( column );
	}

	public void avoid( Column column ) {
		avoidedColumns.add( column );
	}

	public boolean decided() {
		return move != null;
	}

	public Column getColumn() {
		return move;
	}
}
