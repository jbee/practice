package de.jbee.poker.card;

import de.jbee.poker.card.deck.Deck;

/**
 * In distinction to a {@link CardSet} a collection of {@link Cards} contains
 * the cards in a specific order.
 * 
 * Cards for an poker hand has to be ordered so that the highest hand card is
 * the first card of the collection. This is especially important for wheel
 * hands having an ace as its least (not first) card.
 * 
 * Every pack of cards is an immutable collection so neither
 * {@link #joinWith(Cards)} nor {@link #subtract(Cards)} will change the set on
 * which the method is called. The results are always new collection of cards.
 * 
 * Another constrain of an cards collection is that only cards of same
 * {@link Deck} may be mixed up by join or subtract them. This ensures never get
 * confused with different decks of cards.
 * 
 * @author jan
 * 
 */
public interface Cards extends Iterable<Card>, Comparable<Cards> {

	public Deck getDeck();

	public CardSet asSet();

	public int count();

	public Card at(int i) throws IndexOutOfBoundsException;

	public Cards joinWith(Cards cards);

	public Cards subtract(Cards cards);

}
