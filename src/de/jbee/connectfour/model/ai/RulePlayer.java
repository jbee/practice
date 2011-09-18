package de.jbee.connectfour.model.ai;

import java.util.ArrayList;
import java.util.List;

import de.jbee.connectfour.model.Game;
import de.jbee.connectfour.model.Player;

/**
 * A rule player makes its moves by using its first rule that applies to the game situation.
 * 
 * @author Jan Bernitt (jan.bernitt@gmx.de)
 */
public class RulePlayer
		implements Player {

	private final List<Rule> rules;

	public RulePlayer( List<Rule> rules ) {
		this.rules = rules;
	}

	@Override
	public void makeMove( Game game ) {
		Move context = new Move();
		for ( Rule r : rules ) {
			r.execute( game, context );
			if ( context.decided() ) {
				game.placeDiscInColumn( context.getColumn() );
				return;
			}
		}
	}

	public static Player newDefault() {
		List<Rule> rules = new ArrayList<Rule>();
		rules.add( new StartInMiddleRule() );
		rules.add( new AvoidD2Rule() );
		rules.add( new TwoInDRule() );
		rules.add( new WinRule() );
		rules.add( new PreventLosingRule() );
		rules.add( new BeroRule() );
		rules.add( new PlayableMoveRule() );
		return new RulePlayer( rules );
	}
}
