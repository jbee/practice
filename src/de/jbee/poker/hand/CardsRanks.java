package de.jbee.poker.hand;

import java.util.Comparator;

import de.jbee.poker.card.Card;

public final class CardsRanks {

	private CardsRanks() {
		// util class
	}

	public static interface CardComparator extends Comparator<Card[]> {

	}

	public static final Comparator<Card> RANK = new Comparator<Card>() {

		@Override
		public int compare(Card a, Card b) {
			return a.rank().compareTo(b.rank());
		}

	};

	public static final CardComparator OF_FIRST = new CardComparator() {

		@Override
		public int compare(Card[] a, Card[] b) {
			return RANK.compare(a[0], b[0]);
		}

	};

	public static final CardComparator IN_SEQUENCE = new CardComparator() {

		@Override
		public int compare(Card[] a, Card[] b) {
			final int end = Math.min(a.length, b.length);
			for (int i = 0; i < end; i++) {
				int res = RANK.compare(a[i], b[i]);
				if (res != 0)
					return res;
			}
			return 0;
		}

	};

	public static final CardComparator OF_FULL_HOUSE = new CardComparator() {

		@Override
		public int compare(Card[] a, Card[] b) {
			int res = RANK.compare(a[0], b[0]);
			if (res != 0)
				return res;
			return RANK.compare(a[3], b[3]);
		}

	};

	public static final CardComparator OF_TWO_PAIR = new CardComparator() {

		@Override
		public int compare(Card[] a, Card[] b) {
			int res = RANK.compare(a[0], b[0]);
			if (res != 0)
				return res;
			return RANK.compare(a[2], b[2]);
		}
	};
}
