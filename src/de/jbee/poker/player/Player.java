package de.jbee.poker.player;

import de.jbee.poker.board.Position;
import de.jbee.poker.board.Roles;
import de.jbee.poker.game.Game;
import de.jbee.poker.table.Seat;
import de.jbee.poker.table.Table;

public interface Player {

	public void sitDownAt(Table table, Seat seat);

	public void take(Position position, Roles roles);

	// rollen können sich auch während des spiels ändern
	// außerdem sind position und rolle für jedes spiel verschieden
	// daher nicht sinnvoll direkt im player sondern etwas das
	// pro spiel existiert

	public void drawAt(Game board);
}
