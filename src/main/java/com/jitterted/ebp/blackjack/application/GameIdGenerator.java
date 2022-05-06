package com.jitterted.ebp.blackjack.application;

public class GameIdGenerator {
    long lastId;

    public GameIdGenerator() {
    }

    long idGenerator() {
        return lastId++;
    }
}