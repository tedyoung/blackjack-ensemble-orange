package com.jitterted.ebp.blackjack.domain;

public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Rank rank, Suit suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public int rankValue() {
        return rank().value();
    }

    public Suit suit() {
        return suit;
    }

    public Rank rank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Card {" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!suit.equals(card.suit)) return false;
        return rank.equals(card.rank);
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}
