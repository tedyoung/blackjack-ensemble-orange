package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HandFactoryTest {
    @Test
    public void twoHandsHaveDifferentIds() {
        Hand hand1 = HandFactory.createHand();
        Hand hand2 = HandFactory.createHand();

        assertThat(hand1.getId())
                .isNotEqualTo(hand2.getId());
    }

}