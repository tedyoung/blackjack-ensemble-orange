package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Hand;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Encoder {
    public static String encode(Hand hand) {
        Stream<Card> cards = hand.cards().stream();
        return encode(cards);
    }

    public static String encode(Deck deck) {
        return encode(deck.allCards());
    }

    private static String encode(Stream<Card> cards) {
        return cards
                .map(CardEncoder::encode)
                .collect(Collectors.joining(","));
    }
}
