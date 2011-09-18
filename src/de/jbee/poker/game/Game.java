package de.jbee.poker.game;

import de.jbee.poker.board.Board;

public interface Game {

	Step getRound();

	Board getBoard();

}
