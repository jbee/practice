package de.jbee.poker.hand;

import static de.jbee.poker.hand.CardsRanks.IN_SEQUENCE;
import de.jbee.poker.card.Card;

/**
 * In poker, players construct hands of five cards according to predetermined
 * rules, which vary according to the precise variant of poker being played.
 * These hands are compared using a standard ranking system, and the player with
 * the highest-ranking hand wins that particular deal. Although used primarily
 * in poker, these hand rankings are also used in other card games, and with
 * poker dice.
 * 
 * The strength of a hand is increased by having multiple cards of the same
 * rank, all the cards being from the same suit, or having all the cards with
 * consecutive values. The position of the various possible hands is based on
 * the probability of being randomly dealt such a hand from a well-shuffled
 * deck.
 * 
 * @author jan
 * 
 */
public final class Hand implements Comparable<Hand> {

	private final HandRank rank;
	private final Card[] rankCards;
	private final Card[] kickerCards;

	Hand(HandRank rank, Card[] rankCards, Card[] kickerCards) {
		assert rankCards.length + kickerCards.length == 5;

		this.rank = rank;
		this.rankCards = rankCards;
		this.kickerCards = kickerCards;
	}

	@Override
	public int compareTo(Hand otherHand) {
		int res = rank.compareTo(otherHand.rank);
		if (res != 0)
			return res;
		res = rank.comparator().compare(rankCards, rankCards);
		if (res != 0 || !breaksTieByKickers())
			return res;
		return IN_SEQUENCE.compare(kickerCards, otherHand.kickerCards);
	}

	@Override
	public String toString() {
		return rank + " " + rankCards + " (" + kickerCards + ")";
	}

	public boolean breaksTieByKickers() {
		return kickerCards.length > 0;
	}

}
