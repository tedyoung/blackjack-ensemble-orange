package com.jitterted.ebp.blackjack.application;

public class GameIdGenerator {
    long gameId;

    public GameIdGenerator(long initialGameId) {
        gameId = initialGameId;
    }

    long nextId() {
        return gameId++;
    }
}