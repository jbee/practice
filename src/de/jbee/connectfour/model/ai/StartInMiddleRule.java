package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Column;
import de.jbee.connectfour.model.Game;

final class StartInMiddleRule
		implements Rule {

	@Override
	public void execute( Game game, Move move ) {
		if ( game.board().isEmpty() ) {
			move.makeIn( Column.D );
		}
	}
}
