package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardDisplayTest {

    @Test
    public void displayTenAsString() throws Exception {
        Card card = new Card(Rank.TEN, Suit.CLUBS);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    public void displayNonTenAsString() throws Exception {
        Card card = new Card(Rank.THREE, Suit.HEARTS);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[31m┌─────────┐[1B[11D│3        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        3│[1B[11D└─────────┘");
    }

}
