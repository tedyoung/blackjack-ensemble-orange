package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class DeckTest {

    @Test
    void fullDeckHas52Cards() throws Exception {
        Deck deck = new Deck();

        assertThat(deck.size())
                .isEqualTo(52);
    }

    @Test
    void drawCardFromDeckReducesDeckSizeByOne() throws Exception {
        Deck deck = new Deck();

        deck.draw();

        assertThat(deck.size())
                .isEqualTo(51);
    }

    @Test
    void drawCardFromDeckReturnsValidCard() throws Exception {
        Deck deck = new Deck();

        Card card = deck.draw();

        assertThat(card)
                .isNotNull();

        assertThat(card.rankValue())
                .isGreaterThan(0);
    }

    @Test
    void drawAllCardsResultsInSetOf52UniqueCards() throws Exception {
        Deck deck = new Deck();

        Set<Card> drawnCards = new HashSet<>();
        for (int i = 1; i <= 52; i++) {
            drawnCards.add(deck.draw());
        }

        assertThat(drawnCards)
                .hasSize(52);
    }

    @Test
    void canCreateDeckFromSpecificCards() {
        List<Card> cards = List.of(new Card(Rank.TWO, Suit.HEARTS),
                                   new Card(Rank.ACE, Suit.SPADES));

        Deck deck = new Deck(cards);

        assertThat(deck.size())
                .isEqualTo(2);
        assertThat(deck.allCards())
                .containsExactly(new Card(Rank.TWO, Suit.HEARTS),
                                 new Card(Rank.ACE, Suit.SPADES));
    }

}