package de.jbee.poker.board;

import de.jbee.poker.card.CommunityCards;

public interface Board {

	/**
	 * In poker, the flop refers to the dealing of the first three face-up cards
	 * to the board, or to those three cards themselves, in community card poker
	 * variants, particularly Texas hold 'em and Omaha hold 'em.
	 * 
	 * The three cards are dealt simultaneously following the completion of the
	 * opening round of betting. After the flop, there is a second round of
	 * betting, which is followed by the dealing of a fourth, or turn, card; and
	 * a fifth, or river, card. The three cards are often dealt face-down in a
	 * stack, then the stack is turned face-up and quickly slid to one side to
	 * expose all three cards, such that a player cannot be seen to be reacting
	 * to one particular card.
	 * 
	 * After the flop, a player will have seen five of the seven cards that will
	 * make up their hand at the showdown. While the flop marks the point at
	 * which players have significant information about the value of their hand,
	 * three more betting rounds are still to be played out.
	 * 
	 * @return
	 */
	public CommunityCards getFlop();

	/**
	 * The turn card or fourth street, in poker, is the fourth of five cards
	 * dealt to the board, constituting one face-up community card that each of
	 * the players in the game can use to make up their final hand.
	 * 
	 * Typically found in community card poker games like Texas hold 'em and
	 * Omaha hold 'em, the turn follows the completion of the second round of
	 * betting after the flop, and is immediately followed by a third round of
	 * betting which concludes with the river.
	 * 
	 * @return
	 */
	public CommunityCards getTurn();

	/**
	 * The river card is the final card dealt in a poker hand, to be followed by
	 * a final round of betting and, if necessary, a showdown. In Texas hold 'em
	 * and Omaha hold'em, the river, also called fifth street, is the fifth and
	 * last card to be dealt to the board, after the flop and turn. In
	 * seven-card stud the river is the final downcard dealt to each player,
	 * although in certain circumstances (such as when there are insufficient
	 * cards remaining in the deck to deal a card face down to each player
	 * remaining in the hand) the river is dealt as a face-up community card.
	 * 
	 * The river can change the fortune of a game by delivering one player a
	 * card which they need to beat another player's already completed hand. A
	 * player losing the pot due only to the river card is said to have been
	 * 'rivered' or 'drowned at the river'. Chancing the game on the river card
	 * is called 'living by the river', because of the dangers involved, and
	 * winning is called "sucking out".
	 * 
	 * @return
	 */
	public CommunityCards getRiver();

	/**
	 * All yet places {@link CommunityCards} on the board: No cards, only flop,
	 * flop and turn, flop, turn and river.
	 * 
	 * @return
	 */
	public CommunityCards getCards();
}
