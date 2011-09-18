package de.jbee.poker.card;

import java.util.Iterator;

final class CardBoard {

	private CardBoard() {
		// util class
	}

	private final static Card[] CARDS = makeCardIndex();
	private final static long[] ALL_OF_SUIT = makeSuitBoards();
	private final static long[] ALL_OF_RANK = makeRankBoards();

	private final static int[] CONNECTIONS = makeConnectionRankMap();

	private final static long CLUBS = ALL_OF_SUIT[Suit.CLUBS.ordinal()];
	private final static long DIAMONDS = ALL_OF_SUIT[Suit.DIAMONDS.ordinal()];
	private final static long HEARTS = ALL_OF_SUIT[Suit.HEARTS.ordinal()];
	private final static long SPADES = ALL_OF_SUIT[Suit.SPADES.ordinal()];
	private final static long BOARD_MASK = HEARTS | CLUBS | SPADES | DIAMONDS;

	static long ofCard(Card card) {
		return ofCard(card.suit(), card.rank());
	}

	static long ofCards(Card... cards) {
		long board = 0L;
		for (Card card : cards) {
			board |= CardBoard.ofCard(card);
		}
		return board;
	}

	static long ofCard(Suit suit, Rank rank) {
		return singleBitAt(indexOf(suit, rank));
	}

	static int indexOf(Card card) {
		return indexOf(card.suit(), card.rank());
	}

	static int indexOf(Suit suit, Rank rank) {
		return (rank.ordinal() * 4) + suit.ordinal();
	}

	static int indexOfHighestCardIn(long board) {
		return 64 - Long.numberOfLeadingZeros(board);
	}

	static int indexOfLowestCardIn(long board) {
		return Math.min(63, Long.numberOfTrailingZeros(board) + 1);
	}

	private static long singleBitAt(int bitIndex) {
		--bitIndex;
		return (bitIndex > 31)
			? ((1L << 32) << (bitIndex % 32))
			: (1L << bitIndex);
	}

	private static long singleBitAtHighestCardOf(long board) {
		return singleBitAt(indexOfHighestCardIn(board));
	}

	private static Card[] makeCardIndex() {
		Card[] cards = new Card[64];
		for (Suit suit : Suit.values()) {
			for (Card card : suit) {
				cards[indexOf(card)] = card;
			}
		}
		return cards;
	}

	private static int[] makeConnectionRankMap() {
		int high = Rank.ACE.ordinal();
		int low = Rank._2.ordinal();
		int[] map = new int[high - low + 2];
		int j = 0;
		for (int i = high; i >= low; i--) {
			map[j++] = i;
		}
		map[j] = Rank.ACE.ordinal(); // wheel hands: 5-4-3-2-A
		return map;
	}

	private static long[] makeRankBoards() {
		Rank[] ranks = Rank.values();
		long[] masks = new long[ranks.length];
		for (int rank = Rank._2.ordinal(); rank <= Rank.ACE.ordinal(); rank++) {
			long rankMask = 0;
			for (Suit suit : Suit.values()) {
				rankMask |= CardBoard.ofCard(suit, ranks[rank]);
			}
			masks[rank] = rankMask;
		}
		return masks;
	}

	private static long[] makeSuitBoards() {
		Suit[] suits = Suit.values();
		long[] masks = new long[suits.length];
		for (Suit suit : suits) {
			final int i = suit.ordinal();
			for (Card card : suit) {
				masks[i] |= CardBoard.ofCard(card);
			}
		}
		return masks;
	}

	static long withAllOf(Rank rank) {
		return ALL_OF_RANK[rank.ordinal()];
	}

	static long withAllOf(Suit suit) {
		return ALL_OF_SUIT[suit.ordinal()];
	}

	static Card cardAt(int index) {
		return CARDS[index];
	}

	static Card highestCardOf(long board) {
		return CARDS[indexOfHighestCardIn(board)];
	}

	static Card lowestCardOf(long board) {
		return CARDS[indexOfLowestCardIn(board)];
	}

	static long union(long one, long other) {
		return one | other;
	}

	static long subtract(long minuend, long subtrahend) {
		return minuend & ~subtrahend;
	}

	static long intersection(long one, long other) {
		return one & other;
	}

	static long complementOf(long board) {
		return (~board) & BOARD_MASK;
	}

	static long withHighestNCardsOf(long board, int n) {
		long highestNBoard = 0L;
		for (int i = 0; i < n; i++) {
			highestNBoard |= singleBitAtHighestCardOf(board & ~highestNBoard);
		}
		return highestNBoard;
	}

	static boolean haveAnyCommonCard(long one, long other) {
		return (one & other) > 0L;
	}

	static long withHighestNConnectedCardsOf(long board, int n) {
		int cardNr = 0;
		long straightBoard = 0L;
		for (int rankIndex : CardBoard.CONNECTIONS) {
			long rankBoard = CardBoard.ALL_OF_RANK[rankIndex];
			if (haveAnyCommonCard(rankBoard, board)) {
				if (cardNr == 0)
					straightBoard = 0L;
				cardNr++;
				straightBoard |= singleBitAtHighestCardOf(board & rankBoard);
				if (cardNr == n) {
					return straightBoard;
				}
			} else {
				cardNr = 0;
			}
		}
		return 0L;
	}

	static int countCardsIn(long board) {
		return Long.bitCount(board);
	}

	static int countConnectedCardsIn(long board) {
		int count = 0;
		int actual = 0;
		for (int rankIndex : CardBoard.CONNECTIONS) {
			long rankBoard = CardBoard.ALL_OF_RANK[rankIndex];
			if (haveAnyCommonCard(board, rankBoard)) {
				++actual;
				count = Math.max(count, actual);
			} else {
				actual = 0;
			}
		}
		return count;
	}

	static long filterdBy(long board, CardFilter filter) {
		long resBoard = board;
		CardBoardIterator i = new CardBoardIterator(board);
		while (i.hasNext()) {
			Card card = i.next();
			if (!filter.accept(card)) {
				resBoard &= ~CardBoard.ofCard(card);
			}
		}
		return resBoard;
	}

	static Iterator<Card> iterator(long board) {
		return new CardBoardIterator(board);
	}

	/**
	 * Lowest to highest card.
	 * 
	 * @author jan
	 * 
	 */
	private static final class CardBoardIterator implements Iterator<Card> {

		private long board;
		private int index = 1;

		public CardBoardIterator(long board) {
			super();
			this.board = board;
		}

		@Override
		public boolean hasNext() {
			return board > 0L;
		}

		private void inc() {
			index++;
			board >>>= 1;
		}

		@Override
		public Card next() {
			while (hasNext() && (board & 1L) == 0) {
				inc();
			}
			Card res = CardBoard.cardAt(index);
			inc();
			return res;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(CardSet.class.getSimpleName()
					+ "s are immutable!");
		}
	}

}
