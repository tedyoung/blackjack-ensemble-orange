package com.jitterted.ebp.blackjack.domain;

import java.util.Collections;
import java.util.List;

public class HandFactory {
    private static int handId;

    static Hand createHand() {
        return createHand(Collections.emptyList());
    }

    public static Hand createHand(List<Card> cards) {
        return new Hand(handId++, cards);
    }
}
