package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Game;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameRepository implements GameRepository {
    private final Map<Long, Game> gameMap = new HashMap<>();
    private GameIdGenerator gameIdGenerator;

    public InMemoryGameRepository() {
        this.gameIdGenerator = new GameIdGenerator(0);
    }

    public InMemoryGameRepository(GameIdGenerator gameIdGenerator) {
        this.gameIdGenerator = gameIdGenerator;
    }

    @Override
    public Game save(Game game) {
        if (game.getId() == null) {
            long gameId = gameIdGenerator.nextId();
            game.setId(gameId);
        }
        gameMap.put(game.getId(), game);
        return game;
    }

    @Override
    public Game findById(long id) {
        return gameMap.get(id);
    }
}