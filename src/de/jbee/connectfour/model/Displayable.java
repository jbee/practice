package de.jbee.connectfour.model;

public interface Displayable {

	void turn();

	/**
	 * A.k.a. Player 1 or start player
	 */
	Board attackerBoard();

	/**
	 * A.k.a Player 2
	 */
	Board defenderBoard();

	Board victoriousBoard();

	boolean isFinised();

}