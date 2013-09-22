package de.jbee.poker.card;

import java.util.Iterator;

/**
 * A immutable high performance low memory set of card representation using bit boards. Due a deck
 * of cards only contains 52 cards each card is represented by a single bit of a single
 * <code>long</code> value.
 * 
 * Thereby all operation will run in constant time <b>O(1)</b> (only some methods like the
 * {@link #filteredBy(CardFilter)} operation will run (at least) in <b>O(n)</b> [with n = number of
 * cards in set]).
 * 
 * Due immutability all 'manipulating' method return new result sets as result of their
 * manipulation. The originating set left unchanged. This avoids any side effects.
 * 
 * <h3>Board representation</h3>
 * 
 * A card board uses 52 of the possible 64 bits. It is ordered primary by rank, secondary by suit
 * like shown here:
 * 
 * <pre>
 * AAAA KKKK QQQQ JJJJ TTTT 9999 8888 7777 6666 5555 4444 3333 2222
 * ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣ ♠♥♦♣
 * </pre>
 * 
 * Aces assigned to the 4 most left significant (used) bits, 2th to the most right least significant
 * (used) bits. Thereby a simple hand rating is possible. High numeric <code>long</code> values mean
 * there are some high ranked cards in there at least useful as kickers.
 * 
 * @author Jan Bernitt
 * 
 */
public final class CardSet
		implements Iterable<Card> {

	/*
	 * OPEN can the remaining 11/12 bits be used to add additional aggregate flags ?
	 */

	public final static CardSet EMPTY = new CardSet( 0L );

	/**
	 * A bit-board as created by {@link CardBoard}.
	 */
	private final long setCards;

	private CardSet( long setCards ) {
		this.setCards = setCards;
	}

	private static CardSet setOf( long setCards ) {
		return setCards == 0L
			? EMPTY
			: new CardSet( setCards );
	}

	public int cardinality() {
		return CardBoard.countCardsIn( setCards );
	}

	public CardSet complement() {
		return setOf( CardBoard.complementOf( setCards ) );
	}

	public boolean contains( Card card ) {
		return containsAnyAlsoIn( CardBoard.ofCard( card ) );
	}

	public boolean containsAllOf( CardSet other ) {
		return isSubset( other );
	}

	public boolean containsAllOf( Rank rank ) {
		return containsAtLeastNAlsoIn( CardBoard.withAllOf( rank ), Suit.COUNT );
	}

	public boolean containsAllOf( Suit suit ) {
		return containsAtLeastNAlsoIn( CardBoard.withAllOf( suit ), Rank.COUNT );
	}

	private boolean containsAnyAlsoIn( long otherCards ) {
		return CardBoard.haveAnyCommonCard( setCards, otherCards );
	}

	public boolean containsAnyOf( CardSet other ) {
		return containsAnyAlsoIn( other.setCards );
	}

	public boolean containsAnyOf( Rank rank ) {
		return containsAnyAlsoIn( CardBoard.withAllOf( rank ) );
	}

	public boolean containsAnyOf( Suit suit ) {
		return containsAnyAlsoIn( CardBoard.withAllOf( suit ) );
	}

	private boolean containsAtLeastNAlsoIn( long set, int n ) {
		return countCardsAlsoIn( set ) >= n;
	}

	private int countCardsAlsoIn( long otherCards ) {
		return CardBoard.countCardsIn( CardBoard.intersection( setCards, otherCards ) );
	}

	public int countConnected() {
		return CardBoard.countConnectedCardsIn( setCards );
	}

	public int countCardsOfSameKindWith( Rank rank ) {
		return countCardsAlsoIn( CardBoard.withAllOf( rank ) );
	}

	public int countCardsSuitedWith( Suit suit ) {
		return countCardsAlsoIn( CardBoard.withAllOf( suit ) );
	}

	public boolean isEqualTo( CardSet other ) {
		return setCards == other.setCards;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj == this || ( obj instanceof CardSet && setCards == ( (CardSet) obj ).setCards );
	}

	public CardSet filteredBy( CardFilter filter ) {
		return isEmpty()
			? this
			: setOf( CardBoard.filterdBy( setCards, filter ) );
	}

	@Override
	public int hashCode() {
		return (int) setCards + (int) ( setCards >> 32 );
	}

	public Card highestCard() {
		return CardBoard.highestCardOf( setCards );
	}

	public CardSet highestFlush() {
		return highestNFlush( 5 );
	}

	public CardSet highestFourOfAKind() {
		return highestNOfAKind( 4 );
	}

	public CardSet highestFullHouse() {
		CardSet threeOfAKind = highestThreeOfAKind();
		if ( threeOfAKind == EMPTY ) {
			return EMPTY;
		}
		CardSet pair = this.without( threeOfAKind ).highestOnePair();
		if ( pair == EMPTY ) {
			return EMPTY;
		}
		return threeOfAKind.union( pair );
	}

	public CardSet highestNCards( int n ) {
		if ( n > cardinality() ) {
			return EMPTY;
		}
		return highestNCardsOf( setCards, n );
	}

	private CardSet highestNCardsOf( long set, int n ) {
		return setOf( CardBoard.withHighestNCardsOf( set, n ) );
	}

	public CardSet highestNFlush( int n ) {
		CardSet highestNFlush = EMPTY;
		if ( n > cardinality() ) {
			return highestNFlush;
		}
		for ( Suit suit : Suit.values() ) {
			long suitCards = CardBoard.withAllOf( suit );
			if ( containsAtLeastNAlsoIn( suitCards, n ) ) {
				highestNFlush = CardSet.withHigherKickerCard( highestNFlush, highestNCardsOf(
						CardBoard.intersection( setCards, suitCards ), n ) );
			}
		}
		return highestNFlush;
	}

	public static CardSet withHigherKickerCard( CardSet one, CardSet another ) {
		return one.isEmpty()
			? another
			: another.isEmpty()
				? one
				: one.highestCard().rank().higherThan( another.highestCard().rank() )
					? one
					: another;
	}

	public CardSet highestNOfAKind( int n ) {
		if ( n > cardinality() ) {
			return EMPTY;
		}
		for ( int i = Rank.ACE.ordinal(); i >= Rank._2.ordinal(); i-- ) {
			long rankCards = CardBoard.withAllOf( Rank.of( i ) );
			if ( containsAtLeastNAlsoIn( rankCards, n ) ) {
				return highestNCardsOf( CardBoard.intersection( setCards, rankCards ), n );
			}
		}
		return EMPTY;
	}

	public CardSet highestNStraight( int n ) {
		if ( n > cardinality() ) {
			return EMPTY;
		}
		return highestNStraightOf( setCards, n );
	}

	public CardSet highestNStraightFlush( int n ) {
		if ( n > cardinality() ) {
			return EMPTY;
		}
		for ( Suit suit : Suit.values() ) {
			long suitCards = CardBoard.withAllOf( suit );
			CardSet straightFlush = highestNStraightOf(
					CardBoard.intersection( setCards, suitCards ), n );
			if ( straightFlush != EMPTY ) {
				return straightFlush;
			}
		}
		return EMPTY;
	}

	private CardSet highestNStraightOf( long set, int n ) {
		return setOf( CardBoard.withHighestNConnectedCardsOf( set, n ) );
	}

	public CardSet highestOnePair() {
		return highestNOfAKind( 2 );
	}

	/**
	 * Will not remind that a straight flush is higher even if the cards used are lower than those
	 * of highest straight.
	 * 
	 * @see #highestStraightFlush()
	 */
	public CardSet highestStraight() {
		return highestNStraight( 5 );
	}

	public CardSet highestStraightFlush() {
		return highestNStraightFlush( 5 );
	}

	public CardSet highestThreeOfAKind() {
		return highestNOfAKind( 3 );
	}

	public CardSet highestTwoPair() {
		CardSet pair1 = highestOnePair();
		if ( pair1 == EMPTY ) {
			return EMPTY;
		}
		CardSet pair2 = without( pair1 ).highestOnePair();
		if ( pair2 == EMPTY ) {
			return EMPTY;
		}
		return pair1.union( pair2 );
	}

	public CardSet intersection( CardSet with ) {
		return setOf( CardBoard.intersection( setCards, with.setCards ) );
	}

	public boolean isEmpty() {
		return this == EMPTY;
	}

	public boolean isSubset( CardSet other ) {
		return CardBoard.intersection( setCards, other.setCards ) == other.setCards;
	}

	@Override
	public Iterator<Card> iterator() {
		return CardBoard.iterator( setCards );
	}

	public Card lowestCard() {
		return CardBoard.lowestCardOf( setCards );
	}

	public int countMaximumCardsOfSameKind() {
		int max = 0;
		Rank[] ranks = Rank.values();
		for ( int i = Rank._2.ordinal(); i <= Rank.ACE.ordinal(); i++ ) {
			max = Math.max( max, countCardsOfSameKindWith( ranks[i] ) );
		}
		return max;
	}

	public int countMaximumCardsSuited() {
		int max = 0;
		for ( Suit suit : Suit.values() ) {
			max = Math.max( max, countCardsSuitedWith( suit ) );
		}
		return max;
	}

	public Card[] toArray() {
		return toArray( Order.ASCENDING );
	}

	public Card[] toArray( Order order ) {
		final int length = cardinality();
		Card[] res = new Card[length];
		if ( order == Order.ASCENDING ) {
			int i = 0;
			for ( Card card : this ) {
				res[i++] = card;
			}
		} else {
			int i = length;
			for ( Card card : this ) {
				res[--i] = card;
			}
		}
		return res;
	}

	@Override
	public String toString() {
		if ( isEmpty() ) {
			return "";
		}
		StringBuilder b = new StringBuilder();
		for ( Card card : this ) {
			b.append( ' ' ).append( card );
		}
		return b.substring( 1 );
	}

	public CardSet union( CardSet with ) {
		return setOf( CardBoard.union( setCards, with.setCards ) );
	}

	public CardSet with( Card card ) {
		return setOf( CardBoard.union( setCards, CardBoard.ofCard( card ) ) );
	}

	public CardSet without( Card... cards ) {
		return ( cards == null || cards.length == 0 )
			? this
			: setOf( CardBoard.intersection( setCards, CardBoard.ofCards( cards ) ) );
	}

	public CardSet without( Card card ) {
		return setOf( CardBoard.subtract( setCards, CardBoard.ofCard( card ) ) );
	}

	public CardSet without( CardSet other ) {
		return setOf( CardBoard.subtract( setCards, other.setCards ) );
	}

	//TODO this works completely through public calls - so it can be extracted to a other class - named like HoldemCardSet because this is Holdem specific ranking 
	// Aufbauen einer CHAIN, - so wird man die unübersichtlichen if's los
	public CardSet bestHandCards() {
		final int c = cardinality();
		if ( c == 0 ) {
			return this;
		}
		CardSet bestHand = EMPTY;
		if ( c >= 5 ) {
			bestHand = highestStraightFlush();
		}
		if ( bestHand.isEmpty() && c >= 4 ) {
			bestHand = highestFourOfAKind();
		}
		if ( bestHand.isEmpty() && c >= 5 ) {
			bestHand = highestFullHouse();
			if ( bestHand.isEmpty() ) {
				bestHand = highestFlush();
			}
			if ( bestHand.isEmpty() ) {
				bestHand = highestStraight();
			}
		}
		if ( bestHand.isEmpty() && c >= 3 ) {
			bestHand = highestThreeOfAKind();
		}
		if ( bestHand.isEmpty() && c >= 4 ) {
			bestHand = highestTwoPair();
		}
		if ( bestHand.isEmpty() && c >= 2 ) {
			bestHand = highestOnePair();
		}
		if ( !bestHand.isEmpty() ) {
			final int bhc = bestHand.cardinality();
			if ( bhc == 5 ) {
				return bestHand;
			}
			if ( c > bhc ) {
				return bestHand.union( without( bestHand ).highestNCards( 5 - bhc ) );
			}
			return bestHand; // not 5 cards but no kickers to fill available
		}
		return this; // there must be only 1 card - the kicker 
	}
}