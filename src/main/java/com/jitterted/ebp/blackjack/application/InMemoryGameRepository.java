package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameRepository implements GameRepository {
    private final Map<Long, Game> gameMap = new HashMap<>();

    @Override
    public Game save(Game game) {
        return gameMap.put(game.getId(), game);
    }

    @Override
    public Game findById(long id) {
        return gameMap.get(id);
    }
}