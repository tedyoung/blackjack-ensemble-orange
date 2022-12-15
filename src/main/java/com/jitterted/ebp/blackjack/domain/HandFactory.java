package com.jitterted.ebp.blackjack.domain;

import java.util.List;

public class HandFactory {
    private static int handId;

    static Hand createHand() {
        return new Hand(handId++);
    }

    public static Hand createHand(List<Card> cards) {
        return new Hand(handId++, cards);
    }
}
