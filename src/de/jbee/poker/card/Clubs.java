package de.jbee.poker.card;

public enum Clubs implements Card {

	_2, _3, _4, _5, _6, _7, _8, _9, _10, JACK, QUEEN, KING, ACE;

	@Override
	public Suit suit() {
		return Suit.CLUBS;
	}

	@Override
	public Rank rank() {
		return Rank.of(this);
	}

	@Override
	public int quality() {
		return Rank.qualityOf(this);
	}

	@Override
	public String toString() {
		return Suit.toString(this);
	}
}
