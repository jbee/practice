package de.jbee.poker.card;

public enum Rank {

	NOTHING('-'), JOKER('?'), // 
	_2('2'), _3('3'), _4('4'), _5('5'), _6('6'), _7('7'), _8('8'), _9('9'), //
	_10('T'), JACK('J'), QUEEN('Q'), KING('K'), ACE('A');

	public static final int COUNT = Rank.values().length;

	private static final Rank[] RANKS = values();

	private final String initial;

	private Rank(char initial) {
		this.initial = String.valueOf(initial);
	}

	public static Rank of(Card card) {
		return RANKS[card.quality()];
	}

	public static Rank of(int ordinal) {
		return RANKS[ordinal];
	}

	public boolean higherThan(Rank other) {
		return ordinal() > other.ordinal();
	}

	public boolean lowerThan(Rank other) {
		return ordinal() < other.ordinal();
	}

	/**
	 * Ignores the A-2 connection.
	 */
	public int distanceTo(Rank other) {
		return Math.abs(this.ordinal() - other.ordinal());
	}

	/**
	 * Keeps the A-2 connection in mind.
	 */
	public boolean connectedTo(Rank other) {
		return distanceTo(other) == 1 || isTuple(other, ACE, _2);
	}

	private boolean isTuple(Rank other, Rank tuple1, Rank tuple2) {
		return (this == tuple1 && other == tuple2) || (this == tuple2 && other == tuple1);
	}

	/**
	 * J-Q-K
	 */
	public boolean isFaceCard() {
		return isBetween(JACK, KING);
	}

	/**
	 * A-2-3-4-5
	 */
	public boolean isWheelCard() {
		return isOnOf(ACE, _2, _3, _4, _5);
	}

	/**
	 * 4-5-6-7-8-9-10
	 */
	public boolean isSpotterCard() {
		return isBetween(_4, _10);
	}

	/**
	 * A-2-3
	 */
	public boolean isNoSpotterCard() {
		return isOnOf(ACE, _2, _3);
	}

	/**
	 * A-2-3-4-5-6-7-8-9-10
	 */
	public boolean isWhiteskinCard() {
		return isSpotterCard() || isNoSpotterCard();
	}

	/**
	 * A-K-Q-J-10
	 */
	public boolean isHonorCard() {
		return isBetween(_10, ACE);
	}

	/**
	 * Low and high are both inclusive.
	 */
	public boolean isBetween(Rank low, Rank high) {
		return ordinal() >= low.ordinal() && ordinal() <= high.ordinal();
	}

	public boolean isOnOf(Rank... ranks) {
		for (Rank rank : ranks) {
			if (rank == this)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return initial;
	}

	public static Rank valueOf(int ordinal) {
		return RANKS[ordinal];
	}

	public static <C extends Enum<C> & Card> int qualityOf(C card) {
		return card.ordinal() + 2;
	}

	public Rank next() {
		return this != ACE
			? RANKS[ordinal() + 1]
			: null;
	}

}
