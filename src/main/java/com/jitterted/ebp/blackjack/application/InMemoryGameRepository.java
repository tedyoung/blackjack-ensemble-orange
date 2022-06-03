package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryGameRepository implements GameRepository {
    private final Map<Long, Game> gameMap = new HashMap<>();

    @Override
    public Game save(Game game) {
        gameMap.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(long id) {
        return Optional.ofNullable(gameMap.get(id));
    }
}