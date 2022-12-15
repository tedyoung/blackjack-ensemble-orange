package com.jitterted.ebp.blackjack.domain;

public class HandFactory {
    private static int handId;

    static Hand createHand() {
        return new Hand(handId++);
    }
}
