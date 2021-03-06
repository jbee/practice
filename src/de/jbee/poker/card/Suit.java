package de.jbee.poker.card;

import java.util.Arrays;
import java.util.Iterator;

import de.jbee.poker.card.deck.Deck;
import de.jbee.poker.card.pack.Pack;

/**
 * In playing cards, a suit is one of several categories into which the cards of
 * a deck are divided. Most often, each card bears one of several symbols
 * showing to which suit it belongs; the suit may alternatively or in addition
 * be indicated by the color printed on the card. Most card decks also have a
 * rank for each card, and may include special cards in the deck that belong to
 * no suit.
 * 
 * The four French playing card suits used primarily in the English-speaking
 * world: spades(♠), hearts(♥), diamonds(♦) and clubs(♣).
 * 
 * @author jan
 * 
 */
public enum Suit implements Iterable<Card> {

	SPADES('♠', Spades.class), //
	HEARTS('♥', Hearts.class), //
	DIAMONDS('♦', Diamonds.class), //
	CLUBS('♣', Clubs.class);

	public static final int COUNT = Suit.values().length;

	private final String initial;
	private final Card[] cards;

	<E extends Enum<E> & Card> Suit(char initial, Class<E> suitType) {
		this.initial = String.valueOf(initial);
		this.cards = suitType.getEnumConstants();
	}

	public Cards allCardsFrom(Deck deck) {
		switch (this) { // because of generics use switch here
		default:
		case SPADES:
			return Pack.of(deck, Spades.class);
		case HEARTS:
			return Pack.of(deck, Hearts.class);
		case CLUBS:
			return Pack.of(deck, Clubs.class);
		case DIAMONDS:
			return Pack.of(deck, Diamonds.class);
		}
	}

	@Override
	public String toString() {
		return initial;
	}

	public static <C extends Enum<C> & Card> String toString(C card) {
		return card.suit().toString() + card.rank().toString();
	}

	@Override
	public Iterator<Card> iterator() {
		return Arrays.asList(cards).iterator();
	}

	public Card cardWith(Rank rank) {
		return cards[rank.ordinal() - 2];
	}

}
