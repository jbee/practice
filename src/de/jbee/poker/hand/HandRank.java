package de.jbee.poker.hand;

import java.util.Comparator;

import de.jbee.poker.card.Card;
import de.jbee.poker.card.CardSet;
import de.jbee.poker.card.Order;
import de.jbee.poker.card.Rank;
import de.jbee.poker.hand.CardsRanks.CardComparator;
import de.jbee.poker.util.Arrays;

public enum HandRank {

	HIGH_CARD (1302540, HandPickers.HIGH_CARD, CardsRanks.IN_SEQUENCE), //
	ONE_PAIR (1098240, HandPickers.ONE_PAIR, CardsRanks.OF_FIRST), //
	TWO_PAIR (123552, HandPickers.TWO_PAIR, CardsRanks.OF_TWO_PAIR), //
	THREE_OF_A_KIND (54912, HandPickers.THREE_OF_A_KIND, CardsRanks.OF_FIRST), //
	STRAIGHT (10200, HandPickers.STRAIGHT, CardsRanks.OF_FIRST), //
	FLUSH (5108, HandPickers.FLUSH, CardsRanks.IN_SEQUENCE), //
	FULL_HOUSE (3744, HandPickers.FULL_HOUSE, CardsRanks.OF_FULL_HOUSE), //
	FOUR_OF_A_KIND (624, HandPickers.FOUR_OF_A_KIND, CardsRanks.OF_FIRST), //
	STRAIGHT_FLUSH (36, HandPickers.STRAIGHT_FLUSH, CardsRanks.OF_FIRST);

	/**
	 * Which is:
	 * 
	 * <pre>
	 * (52 * 51 * 50 * 49 * 48) / 5! = (52! / (52-5)!) / 5!
	 * </pre>
	 */
	public static final float PROBABILITY_DIVISIOR = 2598960f;

	private static class RankCards {

		final HandRank rank;
		final CardSet cards;

		public RankCards(HandRank rank, CardSet cards) {
			this.rank = rank;
			this.cards = cards;
		}
	}

	private final HandPicker picker;
	private final CardComparator comparator;
	private final int frequency;
	private final float probability;

	HandRank(int frequency, HandPicker picker, CardComparator comparator) {
		this.frequency = frequency;
		this.probability = frequency / PROBABILITY_DIVISIOR;
		this.picker = picker;
		this.comparator = comparator;
	}

	public Hand rank(CardSet allCards) {
		RankCards rankCards = computeRankCardsOf(allCards);
		return new Hand(rankCards.rank, adjustRankCards(rankCards),
				selectKickerCardsOf(rankCards, allCards));
	}

	private RankCards computeRankCardsOf(CardSet cards) {
		final HandRank[] ranks = HandRank.values();
		for (int i = ranks.length - 1; i >= 0; i--) {
			HandRank rank = ranks[i];
			CardSet rankCards = rank.picker.pickHandCards(cards);
			if (!rankCards.isEmpty())
				return new RankCards(rank, rankCards);
		}
		throw new IllegalArgumentException("No ranking conform to cards: "
				+ cards);
	}

	private Card[] selectKickerCardsOf(RankCards rankCards, CardSet allCards) {
		int kickers = rankCards.cards.cardinality() - 5;
		return (kickers == 0)
			? new Card[0]
			: allCards.without(rankCards.cards).highestNCards(kickers).toArray(
					Order.DESCENDING);
	}

	private Card[] adjustRankCards(RankCards rankCards) {
		Card[] res = rankCards.cards.toArray(Order.DESCENDING);
		return isWheelHand(rankCards)
			? Arrays.rotate1Left(res)
			: res;
	}

	private boolean isWheelHand(RankCards rankCards) {
		return (rankCards.rank.isStraight())
				&& rankCards.cards.containsAnyOf(Rank.ACE)
				&& rankCards.cards.containsAnyOf(Rank._2);
	}

	/**
	 * @return the (absolute) frequency of each hand, given all combinations of
	 *         5 cards randomly drawn from a full deck of 52 without
	 *         replacement. Wild cards are not considered.
	 */
	public int getFrequency() {
		return frequency;
	}

	public float probability() {
		return probability;
	}

	public Comparator<Card[]> comparator() {
		return comparator;
	}

	public boolean isStraight() {
		return this == STRAIGHT || this == STRAIGHT_FLUSH;
	}
}
