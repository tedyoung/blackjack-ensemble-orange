package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

class HandFactoryTest {
    @Test
    public void twoHandsHaveDifferentIds() {
        Hand hand1 = HandFactory.createHand();
        Hand hand2 = HandFactory.createHand();

        assertThat(hand1.getId())
                .isNotEqualTo(hand2.getId());
    }

    @Test
    public void canCreateHandWithCards() {
        Hand hand = HandFactory.createHand(List.of(new Card(Rank.TEN, Suit.HEARTS)));

        assertThat(hand.cards())
            .hasSize(1);
    }

}