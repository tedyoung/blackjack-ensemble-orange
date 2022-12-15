package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

// Entity
public class Hand {

    private final List<Card> cards = new ArrayList<>();
    private final int id;

    public int getId() {
        return id;
    }

    public Hand(int id) {
        this.id = id;
    }

    public Hand(int id, List<Card> cards) {
        this.id = id;
        this.cards.addAll(cards);
    }

    public int value() {
        int handValue = cards
                .stream()
                .mapToInt(Card::rankValue)
                .sum();

        // does the hand contain at least 1 Ace?
        boolean hasAce = cards
                .stream()
                .anyMatch(card -> card.rankValue() == 1);

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (hasAce && handValue <= 11) {
            handValue += 10;
        }

        return handValue;
    }

    public Card faceUpCard() {
        return cards.get(0);
    }

    boolean dealerMustDrawCard() {
        return value() <= 16;
    }

    // SNAPSHOT of information ("point in time")
    // immutable: don't want clients/consumers to change this object
    public List<Card> cards() {
        return List.copyOf(cards);
    }

    public void drawFrom(Deck deck) {
        cards.add(deck.draw());
    }

    boolean isBusted() {
        return value() > 21;
    }

    boolean pushes(Hand hand) {
        return hand.value() == value();
    }

    boolean beats(Hand hand) {
        return hand.value() < value();
    }

    public boolean hasBlackjack() {
        return value() == 21 && cards.size() == 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hand hand = (Hand) o;

        return id == hand.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
