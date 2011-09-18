package de.jbee.poker.hand;

import de.jbee.poker.card.CardSet;

public final class RankRules {

	private static final class AndRankRule implements RankRule {

		private final RankRule first;
		private final RankRule second;

		AndRankRule(RankRule first, RankRule second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public boolean match(CardSet cards) {
			return first.match(cards) && second.match(cards);
		}
	}

	public static final RankRule FLUSH = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			return cards.countMaximumCardsSuited() >= 5;
		}

	};

	public static final RankRule FOUR_OF_A_KIND = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			return cards.countMaximumCardsOfSameKind() == 4;
		}
	};

	public static final RankRule FULL_HOUSE = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			if (cards.countMaximumCardsOfSameKind() != 3)
				return false;
			CardSet threeOfAKind = cards.highestThreeOfAKind();
			return cards.without(threeOfAKind).countMaximumCardsOfSameKind() == 2;
		}
	};

	public static final RankRule ONE_PAIR = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			return cards.countMaximumCardsOfSameKind() == 2;
		}
	};

	public static final RankRule STRAIGHT = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			return cards.countConnected() >= 5;
		}

	};

	public static final RankRule STRAIGHT_FLUSH = both(STRAIGHT, FLUSH);

	public static final RankRule THREE_OF_A_KIND = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			return cards.countMaximumCardsOfSameKind() == 3;
		}
	};

	public static final RankRule TWO_PAIR = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			if (cards.countMaximumCardsOfSameKind() != 2)
				return false;
			CardSet onePair = cards.highestOnePair();
			return cards.without(onePair).countMaximumCardsOfSameKind() == 2;
		}
	};

	public static final RankRule HIGH_CARD = new RankRule() {

		@Override
		public boolean match(CardSet cards) {
			return cards.cardinality() > 0;
		}

	};

	public static RankRule all(RankRule... rules) {

		RankRule res = rules[0];
		for (int i = 1; i < rules.length; i++) {
			res = both(res, rules[i]);
		}
		return res;
	}

	public static RankRule both(RankRule r1, RankRule r2) {
		return new AndRankRule(r1, r2);
	}
}
