package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Column;
import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.Position;
import de.jbee.connectfour.model.Row;

public class TwoInDRule
		implements Rule {

	@Override
	public void execute( Game game, Move move ) {
		int row = game.board().playableRowInColumn( Column.D ).ordinal();
		if ( row > 0 && !move.isAvoided( Column.D )
				&& game.ownBoard().taken( new Position( Column.D, Row.values()[row - 1] ) ) ) {
			move.makeIn( Column.D );
		}
	}

}
