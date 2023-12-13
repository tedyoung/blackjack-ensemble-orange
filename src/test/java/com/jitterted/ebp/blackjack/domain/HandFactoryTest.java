package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HandFactoryTest {
    @Test
    void twoHandsHaveDifferentIds() {
        Hand hand1 = HandFactory.createHand();
        Hand hand2 = HandFactory.createHand();

        assertThat(hand1.getId())
                .isNotEqualTo(hand2.getId());
    }

    @Test
    void canCreateHandWithCards() {
        Hand hand = HandFactory.createHand(List.of(new Card(Rank.TEN, Suit.HEARTS)));

        assertThat(hand.cards())
                .containsExactly(new Card(Rank.TEN, Suit.HEARTS));
    }

}