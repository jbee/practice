package de.jbee.connectfour.model.ai;

import de.jbee.connectfour.model.Game;

public interface Rule {

	void execute( Game game, Move move );

}
