package de.jbee.poker.card.deck;

import de.jbee.poker.card.Card;
import de.jbee.poker.card.Cards;

/**
 * A complete set of cards (52) is called a pack or deck, and the set of cards
 * held at one time by a player during a game is commonly called their hand.
 * 
 * @author jan
 * 
 */
public interface Deck {

	/**
	 * divide a pack of playing cards by lifting a portion from the top.
	 * 
	 * @return
	 */
	public Cards cut(int portion);

	public Card draw();

	public Card burn();

	public void shufle();

	/**
	 * A pack of not yet drawn cards of this deck.
	 */
	public Pile getDrawPile();

	/**
	 * A pack of already discarded cards (burned).
	 */
	public Pile getDiscardPile();
}
