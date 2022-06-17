package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.StubDeck;
import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DeckEncoderTest {
    @Test
    public void emptyDeckEncodedAsEmptyString() throws Exception {
        Deck deck = new Deck();

        String encodedDeck = DeckEncoder.encode(deck);

        assertThat(encodedDeck)
                .isEmpty();
    }

    @Test
    public void deckWithOneCardEncodedAsCardString() throws Exception {
        Deck deck = new StubDeck(List.of(new Card(Suit.HEARTS, Rank.TEN)));

        String encodedDeck = DeckEncoder.encode(deck);

        assertThat(encodedDeck)
                .isEqualTo("TH");
    }

    public static class DeckEncoder {

        public static String encode(Deck deck) {
            return "";
        }
    }
}
