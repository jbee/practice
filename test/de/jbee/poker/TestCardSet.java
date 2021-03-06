package de.jbee.poker;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;
import de.jbee.poker.card.Card;
import de.jbee.poker.card.CardSet;
import de.jbee.poker.card.Clubs;
import de.jbee.poker.card.Diamonds;
import de.jbee.poker.card.Hearts;
import de.jbee.poker.card.Rank;
import de.jbee.poker.card.Spades;
import de.jbee.poker.card.Suit;

public class TestCardSet extends TestCase {

	private CardSet cardSet = CardSet.EMPTY;
	private final Set<Card> refSet = new HashSet<Card>();
	private final Random rnd = new Random();

	public void testAllOf() {
		fail("Not yet implemented");
	}

	public void testAnyOf() {
		fail("Not yet implemented");
	}

	public void testAnyOfBoard() {
		fail("Not yet implemented");
	}

	public void testAnyOfRank() {
		fail("Not yet implemented");
	}

	public void testAnyOfSuit() {
		fail("Not yet implemented");
	}

	public void testAtLeastNOfBoard() {
		fail("Not yet implemented");
	}

	public void testComplement() {
		fail("Not yet implemented");
	}

	public void testCountConnected() {
		fail("Not yet implemented");
	}

	public void testCountSameKind() {
		fail("Not yet implemented");
	}

	public void testCountSuited() {
		fail("Not yet implemented");
	}

	public void testEqualsCardSet() {
		fail("Not yet implemented");
	}

	public void testFilter() {
		fail("Not yet implemented");
	}

	public void testHighestCard() {
		fail("Not yet implemented");
	}

	public void testHighestFlush() {
		fail("Not yet implemented");
	}

	public void testHighestFourOfAKind() {
		fail("Not yet implemented");
	}

	public void testHighestFullHouse() {
		fail("Not yet implemented");
	}

	public void testHighestPair() {
		fail("Not yet implemented");
	}

	public void testHighestStraight() {

		Card[] straightA = { Hearts._6, Clubs._5, Spades._4, Diamonds._3,
				Hearts._2 };
		insert(straightA);
		assertSame(straightA, cardSet.highestStraight().toArray());

		insert(Hearts.ACE);
		assertSame(straightA, cardSet.highestStraight().toArray());

		remove(Hearts._6);
		assertSame(new Card[] { Clubs._5, Spades._4, Diamonds._3, Hearts._2,
				Hearts.ACE }, cardSet.highestStraight().toArray());
	}

	public void testHighestStraightFlush() {
		fail("Not yet implemented");
	}

	public void testHighestThreeOfAKind() {
		fail("Not yet implemented");
	}

	public void testInsert() {
		fail("Not yet implemented");
	}

	public void testIntersection() {
		fail("Not yet implemented");
	}

	public void testIsEmpty() {

		assertEquals(true, cardSet.isEmpty());

		insert(Hearts._10);
		assertEquals(false, cardSet.isEmpty());
	}

	public void testIsIn() {

		assertEquals(false, cardSet.contains(Hearts.ACE));

		insert(Hearts.ACE);
		assertEquals(true, cardSet.contains(Hearts.ACE));

		insertStraightAscendingFrom(Rank._6, 5);
		assertCardSetEqualsRefSet();
	}

	public void testIsSubset() {
		fail("Not yet implemented");
	}

	public void testIterator() {
		fail("Not yet implemented");
	}

	public void testLowestCard() {

		assertNull(cardSet.highestCard());

		insert(Diamonds._10);
		assertSame(Diamonds._10, cardSet.highestCard());

		insert(Diamonds._9);
		assertSame(Diamonds._10, cardSet.highestCard());

		insert(Diamonds.ACE);
		assertSame(Diamonds.ACE, cardSet.highestCard());
	}

	public void testRemove() {
		fail("Not yet implemented");
	}

	public void testRemoveAll() {

		CardSet tmp = cardSet.without((Card[]) null);
		assertSame(cardSet, tmp);

		tmp = cardSet.without(new Card[0]);
		assertSame(cardSet, tmp);

		insert(Hearts._2, Hearts._4, Diamonds._3, Spades.ACE);
		tmp = cardSet.without(Hearts._2, Clubs._3, Spades.ACE, Clubs._6);
		assertOnlyContains(tmp, Hearts._4, Diamonds._3);
	}

	public void testSubtract() {
		fail("Not yet implemented");
	}

	public void testUnion() {
		fail("Not yet implemented");
	}

	private void assertCardSetEqualsRefSet() {
		for (Suit suit : Suit.values()) {
			for (Card card : suit) {
				assertEquals(refSet.contains(card), cardSet.contains(card));
			}
		}
	}

	private void assertSame(Card[] expected, Card[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			assertSame(expected[i], actual[i]);
		}
	}

	private void assertOnlyContains(CardSet set, Card... cards) {
		CardSet ref = CardSet.EMPTY;
		for (Card card : cards) {
			ref = ref.with(card);
		}
		for (Suit suit : Suit.values()) {
			for (Card card : suit) {
				assertEquals(ref.contains(card), set.contains(card));
			}
		}
	}

	private void insert(Card... cards) {
		for (Card card : cards) {
			insert(card);
		}
	}

	private void insert(Card card) {
		if (card != null) {
			cardSet = cardSet.with(card);
			refSet.add(card);
		}
	}

	private void insertRandomStraight(int length) {
		insertStraightAscendingFrom(Rank.values()[Math.max(Rank._2.ordinal(),
				rnd.nextInt(Rank.ACE.ordinal() - length))], length);
	}

	private void insertStraightAscendingFrom(Rank lowestRank, int length) {
		Suit[] suits = Suit.values();
		Rank rank = lowestRank;
		for (int i = 0; i < length; i++) {
			insert(suits[rnd.nextInt(suits.length)].cardWith(rank));
			rank = rank.next();
		}
	}

	private void remove(Card card) {
		if (card != null) {
			cardSet = cardSet.without(card);
			refSet.remove(card);
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cardSet = CardSet.EMPTY;
		refSet.clear();
	}

}
