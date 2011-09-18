package de.jbee.connectfour.model;

public final class Game
		implements Displayable {

	private Board attackerBoard = Board.EMPTY;
	private Board defenderBoard = Board.EMPTY;
	private Board victoriousBoard = Board.EMPTY;

	private final Player attacker;
	private final Player defender;

	public Game( Player attacker, Player defender ) {
		this.attacker = attacker;
		this.defender = defender;
	}

	public void placeDiscInColumn( Column column ) {
		Position move = new Position( column, board().playableRowInColumn( column ) );
		if ( board().taken( move ) ) {
			throw new IllegalArgumentException( move + " already taken" );
		}
		if ( isAttackerTurn() ) {
			attackerBoard = attackerBoard.place( move );
			victoriousBoard = attackerBoard.victorious( move );
		} else {
			defenderBoard = defenderBoard.place( move );
			victoriousBoard = defenderBoard.victorious( move );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jbee.connectfour.model.Displayable#turn()
	 */
	public void turn() {
		if ( isFinised() ) {
			return;
		}
		if ( isAttackerTurn() ) {
			attacker.makeMove( this );
		} else {
			defender.makeMove( this );
		}
	}

	@Override
	public Board victoriousBoard() {
		return victoriousBoard;
	}

	public boolean isAttackerTurn() {
		return attackerBoard.placedDiscs() <= defenderBoard.placedDiscs();
	}

	public boolean isDefenderTrun() {
		return !isAttackerTurn();
	}

	@Override
	public boolean isFinised() {
		return !victoriousBoard.isEmpty() || playedTurns() == 42;
	}

	public int playedTurns() {
		return board().placedDiscs() + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jbee.connectfour.model.Displayable#attackerBoard()
	 */
	public Board attackerBoard() {
		return attackerBoard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jbee.connectfour.model.Displayable#defenderBoard()
	 */
	public Board defenderBoard() {
		return defenderBoard;
	}

	public Board ownBoard() {
		return isAttackerTurn()
			? attackerBoard
			: defenderBoard;
	}

	public Board opponentBoard() {
		return isAttackerTurn()
			? defenderBoard
			: attackerBoard;
	}

	public Board board() {
		return attackerBoard.merge( defenderBoard );
	}
}
