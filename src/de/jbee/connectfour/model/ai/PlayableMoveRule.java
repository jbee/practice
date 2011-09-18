package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Board;
import de.jbee.connectfour.model.Column;
import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.Row;

public class PlayableMoveRule
		implements Rule {

	@Override
	public void execute( Game game, Move move ) {
		Board gameBoard = game.board();
		for ( Column col : Column.SEQ ) {
			Row row = gameBoard.playableRowInColumn( col );
			if ( row != null ) {
				move.makeIn( col );
				return;
			}
		}
	}

}
