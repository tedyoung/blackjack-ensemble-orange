package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CardDecoderTest {

    @Test
    void cardIsDecodedFromString() {
        Card card = CardDecoder.decode("3S");

        assertThat(card)
                .isEqualTo(new Card(Rank.THREE, Suit.SPADES));
    }

}
