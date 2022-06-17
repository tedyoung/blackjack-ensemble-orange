package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;

import java.util.Map;

import static java.util.Map.entry;

class CardEncoder {

    static final Map<Rank, String> RANKS = Map.ofEntries(
            entry(Rank.ACE, "A"),
            entry(Rank.TWO, "2"),
            entry(Rank.THREE, "3"),
            entry(Rank.FOUR, "4"),
            entry(Rank.FIVE, "5"),
            entry(Rank.SIX, "6"),
            entry(Rank.SEVEN, "7"),
            entry(Rank.EIGHT, "8"),
            entry(Rank.NINE, "9"),
            entry(Rank.TEN, "T"),
            entry(Rank.JACK, "J"),
            entry(Rank.QUEEN, "Q"),
            entry(Rank.KING, "K")
    );

    static final Map<Suit, String> SUITS = Map.of(
            Suit.HEARTS, "H",
            Suit.CLUBS, "C",
            Suit.SPADES, "S",
            Suit.DIAMONDS, "D"
    );

    static String encode(Card card) {
        String encodedRank = RANKS.get(card.rank());
        String encodedSuit = SUITS.get(card.suit());

        return encodedRank + encodedSuit;
    }

}
