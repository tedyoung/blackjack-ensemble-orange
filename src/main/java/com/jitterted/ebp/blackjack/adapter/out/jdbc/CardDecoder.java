package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;

import java.util.Map;

import static java.util.Map.entry;

public class CardDecoder {
    static final Map<Character, Rank> RANKS = Map.ofEntries(
            entry('A', Rank.ACE),
            entry('2', Rank.TWO),
            entry('3', Rank.THREE),
            entry('4', Rank.FOUR),
            entry('5', Rank.FIVE),
            entry('6', Rank.SIX),
            entry('7', Rank.SEVEN),
            entry('8', Rank.EIGHT),
            entry('9', Rank.NINE),
            entry('T', Rank.TEN),
            entry('J', Rank.JACK),
            entry('Q', Rank.QUEEN),
            entry('K', Rank.KING)
    );

    static final Map<Character, Suit> SUITS = Map.of(
             'H',Suit.HEARTS,
             'C',Suit.CLUBS,
             'S',Suit.SPADES,
             'D',Suit.DIAMONDS
    );

    static Card decode(String encoded) {
        return new Card(RANKS.get(encoded.charAt(0)), SUITS.get(encoded.charAt(1)));
    }
}
