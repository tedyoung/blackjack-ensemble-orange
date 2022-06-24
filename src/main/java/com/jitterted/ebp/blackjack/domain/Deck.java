package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Deck {
    protected List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public int size() {
        return cards.size();
    }

    public Card draw() {
        return cards.remove(0);
    }

    public Stream<Card> allCards() {
        return cards.stream();
    }
}
