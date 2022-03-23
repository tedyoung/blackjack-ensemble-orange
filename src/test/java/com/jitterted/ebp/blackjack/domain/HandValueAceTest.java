package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandValueAceTest {

    private static final Suit DUMMY_SUIT = Suit.CLUBS;

    @Test
    public void handWithOneAceAndOtherCardValuedLessThan10ThenAceIsValuedAt11() throws Exception {
        Hand hand = createHand(Rank.ACE, Rank.FIVE);

        assertThat(hand.value())
                .isEqualTo(11 + 5);
    }

    @Test
    public void handWithOneAceAndOtherCardsValuedAt10ThenAceIsValuedAt11() throws Exception {
        Hand hand = createHand(Rank.ACE, Rank.TEN);

        assertThat(hand.value())
                .isEqualTo(11 + 10);
    }

    @Test
    public void handWithOneAceAndOtherCardsValuedAs11ThenAceIsValuedAt1() throws Exception {
        Hand hand = createHand(Rank.ACE, Rank.EIGHT, Rank.THREE);

        assertThat(hand.value())
                .isEqualTo(1 + 8 + 3);
    }

    private Hand createHand(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        return new Hand(cards);
    }

}
