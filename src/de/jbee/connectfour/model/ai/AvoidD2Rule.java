package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Board;
import de.jbee.connectfour.model.Column;
import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.Position;
import de.jbee.connectfour.model.Row;

final class AvoidD2Rule
		implements Rule {

	@Override
	public void execute( Game game, Move move ) {
		if ( game.isAttackerTurn() ) {
			Board gameBoard = game.board();
			if ( gameBoard.taken( new Position( Column.D, Row._1 ) )
					&& !gameBoard.taken( new Position( Column.D, Row._2 ) ) ) {
				move.avoid( Column.D );
			}
		}
	}

}
