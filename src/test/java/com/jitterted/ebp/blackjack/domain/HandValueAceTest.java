package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HandValueAceTest {

    private static final Suit DUMMY_SUIT = Suit.CLUBS;

    @Test
    void handWithOneAceAndOtherCardValuedLessThan10ThenAceIsValuedAt11() throws Exception {
        Hand hand = createHand(Rank.ACE, Rank.FIVE);

        assertThat(hand.value())
                .isEqualTo(11 + 5);
    }

    @Test
    void handWithOneAceAndOtherCardsValuedAt10ThenAceIsValuedAt11() throws Exception {
        Hand hand = createHand(Rank.ACE, Rank.TEN);

        assertThat(hand.value())
                .isEqualTo(11 + 10);
    }

    @Test
    void handWithOneAceAndOtherCardsValuedAs11ThenAceIsValuedAt1() throws Exception {
        Hand hand = createHand(Rank.ACE, Rank.EIGHT, Rank.THREE);

        assertThat(hand.value())
                .isEqualTo(1 + 8 + 3);
    }

    private Hand createHand(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(rank, DUMMY_SUIT));
        }
        return new Hand(0, cards);
    }

}
