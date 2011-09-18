package de.jbee.poker.card.pack;

import de.jbee.poker.card.Card;
import de.jbee.poker.card.Cards;
import de.jbee.poker.card.deck.Deck;

public final class Pack {

	private Pack() {
		// util - hide
	}

	public static Cards of(Cards src, Card singleCard) {

		return null;
	}

	public static Cards of(Cards src, Card one, Card two) {

		return null;
	}

	public static <S extends Enum<S> & Card> Cards of(Deck deck, Class<S> inSuit) {

		return null;
	}

	public static Cards noneOf(Deck deck) {

		return null; // to transport the deck
	}
}
