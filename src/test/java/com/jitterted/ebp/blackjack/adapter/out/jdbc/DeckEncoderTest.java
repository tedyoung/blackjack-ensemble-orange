package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DeckEncoderTest {
    @Test
    void emptyDeckEncodedAsEmptyString() throws Exception {
        Deck deck = new StubDeck();

        String encodedDeck = Encoder.encode(deck);

        assertThat(encodedDeck)
                .isEmpty();
    }

    @Test
    void deckWithOneCardEncodedAsCardString() throws Exception {
        Deck deck = new StubDeck(List.of(new Card(Rank.TEN, Suit.HEARTS)));

        String encodedDeck = Encoder.encode(deck);

        assertThat(encodedDeck)
                .isEqualTo("TH");
    }

    @Test
    void deckWithTwoCardsEncodedAsCommaSeparatedTwoCardString() throws Exception {
        Deck deck = new StubDeck(List.of(new Card(Rank.TEN, Suit.HEARTS),
                                         new Card(Rank.ACE, Suit.HEARTS)));

        String encodedDeck = Encoder.encode(deck);

        assertThat(encodedDeck)
                .isEqualTo("TH,AH");
    }

}
