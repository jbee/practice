package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Board;
import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.Position;

final class WinRule
		implements Rule {

	@Override
	public void execute( Game game, Move move ) {
		Position victoriousMove = Board.victoriousMove( game.board(), game.ownBoard() );
		if ( victoriousMove != null ) {
			move.makeIn( victoriousMove.column );
		}

	}
}
