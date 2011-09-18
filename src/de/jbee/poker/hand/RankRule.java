package de.jbee.poker.hand;

import de.jbee.poker.card.CardSet;

public interface RankRule {

	public boolean match(CardSet cards);
}
