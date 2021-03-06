package de.jbee.poker.hand;

import de.jbee.poker.card.CardSet;

public final class HandPickers {

	private HandPickers() {
		// util class
	}

	public static final HandPicker STRAIGHT_FLUSH = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestStraightFlush();
		}

	};

	public static final HandPicker FOUR_OF_A_KIND = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestFourOfAKind();
		}

	};

	public static final HandPicker FULL_HOUSE = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestFullHouse();
		}

	};

	public static final HandPicker FLUSH = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestFlush();
		}

	};

	public static final HandPicker STRAIGHT = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestStraight();
		}

	};

	public static final HandPicker THREE_OF_A_KIND = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestThreeOfAKind();
		}

	};

	public static final HandPicker TWO_PAIR = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestTwoPair();
		}

	};

	public static final HandPicker ONE_PAIR = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return cards.highestOnePair();
		}

	};

	public static final HandPicker HIGH_CARD = new HandPicker() {

		@Override
		public CardSet pickHandCards(CardSet cards) {
			return CardSet.EMPTY.with(cards.highestCard());
		}

	};
}
