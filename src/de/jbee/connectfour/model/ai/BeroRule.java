package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Board;
import de.jbee.connectfour.model.Column;
import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.Row;

/**
 * BERO stands for <b>B</b>lue in <b>E</b>ven rows and <b>R</b>ed in <b>O</b>dd rows. Assuming blue
 * is the defender color and red the attacker color.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 */
final class BeroRule
		implements Rule {

	@Override
	public void execute( Game game, Move move ) {
		Board gameBoard = game.board();
		if ( game.isDefenderTrun() ) {
			for ( Column col : Column.SEQ ) {
				Row row = gameBoard.playableRowInColumn( col );
				if ( row != null && row.isEven() ) {
					move.makeIn( col );
					return;
				}
			}
		} else {
			for ( Column col : Column.SEQ ) {
				Row row = gameBoard.playableRowInColumn( col );
				if ( row != null && row.isOdd() ) {
					move.makeIn( col );
					return;
				}
			}
		}
	}

}
