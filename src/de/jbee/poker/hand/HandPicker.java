package de.jbee.poker.hand;

import de.jbee.poker.card.CardSet;

public interface HandPicker {

	public CardSet pickHandCards(CardSet from);
}
