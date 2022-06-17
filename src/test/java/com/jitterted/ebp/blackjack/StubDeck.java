package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;

import java.util.ArrayList;
import java.util.List;

public class StubDeck extends Deck {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;

    public StubDeck(Rank... ranks) {
        this.cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(rank, DUMMY_SUIT));
        }
    }

    public StubDeck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public static StubDeck playerHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK,
                            Rank.THREE);
    }

    public static StubDeck playerBeatsDealerUponInitialDeal() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK);
    }

    public static StubDeck playerNotDealtBlackjackHitsAndDoesNotGoBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.SEVEN, Rank.JACK,
                            Rank.THREE);
    }

    public static StubDeck playerNotDealtBlackjackHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.SEVEN, Rank.JACK,
                            Rank.NINE);
    }

    public static StubDeck playerNotDealtBlackjackAndStands() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.SEVEN, Rank.JACK);
    }

    public static StubDeck playerDealtBlackjack() {
        return new StubDeck(Rank.ACE, Rank.NINE,
                            Rank.JACK, Rank.EIGHT);
    }

}
