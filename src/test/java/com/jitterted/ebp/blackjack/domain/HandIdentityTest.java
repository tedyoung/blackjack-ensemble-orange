package com.jitterted.ebp.blackjack.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class HandIdentityTest {

    @Test
    void givenTwoHandsWithSameCardsSecondHandIsNotEqualForDifferentId() {
        Hand handOne = new Hand(1, List.of(new Card(Rank.TWO, Suit.HEARTS), new Card(Rank.ACE, Suit.CLUBS)));
        Hand handTwo = new Hand(2, List.of(new Card(Rank.TWO, Suit.HEARTS), new Card(Rank.ACE, Suit.CLUBS)));

        assertThat(handOne)
                .isNotEqualTo(handTwo);
    }

    @Test
    void givenTwoHandsWithSameIdEqualRegardlessOfCards() {
        Hand handOne = new Hand(1, List.of(new Card(Rank.TWO, Suit.HEARTS), new Card(Rank.ACE, Suit.CLUBS)));
        Hand handTwo = new Hand(1, List.of());

        assertThat(handOne)
                .isEqualTo(handTwo);
    }
}