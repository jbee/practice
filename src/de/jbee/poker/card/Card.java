package de.jbee.poker.card;

public interface Card {

	public Suit suit();

	public Rank rank();

	/**
	 * @return 2 to 13 (Ace)
	 */
	public int quality();

}
