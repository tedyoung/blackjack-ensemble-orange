package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

class CardDisplayTest {
    private static final Rank DUMMY_RANK = Rank.TEN;

    @Test
    void displayTenAsString() throws Exception {
        Card card = new Card(Rank.TEN, Suit.CLUBS);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    void displayNonTenAsString() throws Exception {
        Card card = new Card(Rank.THREE, Suit.HEARTS);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[31m┌─────────┐[1B[11D│3        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        3│[1B[11D└─────────┘");
    }

    @Test
    void suitOfHeartsOrDiamondsIsDisplayedInRed() throws Exception {
        // given a card with Hearts or Diamonds
        Card heartsCard = new Card(DUMMY_RANK, Suit.HEARTS);
        Card diamondsCard = new Card(DUMMY_RANK, Suit.DIAMONDS);

        // when we ask for its display representation
        String ansiRedString = ansi().fgRed().toString();

        // then we expect a red color ansi sequence
        assertThat(ConsoleCard.display(heartsCard))
                .contains(ansiRedString);
        assertThat(ConsoleCard.display(diamondsCard))
                .contains(ansiRedString);
    }

}
