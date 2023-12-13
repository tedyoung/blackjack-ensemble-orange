package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Hand;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HandEncoderTest {

    @Test
    void emptyHandEncodedAsEmptyString() {
        Hand hand = new Hand(1);

        String encodedHand = Encoder.encode(hand);

        assertThat(encodedHand)
                .isEmpty();
    }

    @Test
    void handWithOneCardIsEncodedAsSingleCardString() {
        Hand hand = new Hand(1, List.of(new Card(Rank.JACK, Suit.HEARTS)));

        String encodedHand = Encoder.encode(hand);

        assertThat(encodedHand)
                .isEqualTo("JH");
    }
}
