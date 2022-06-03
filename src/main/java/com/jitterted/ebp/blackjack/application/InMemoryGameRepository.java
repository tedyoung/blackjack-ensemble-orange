package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameRepository {
    private final Map<Long, Game> gameMap = new HashMap<Long, Game>();

    public Map<Long, Game> getGameMap() {
        return gameMap;
    }

    Game save(Game game, GameService gameService) {
        return getGameMap().put(game.getId(), game);
    }

    Game findById(long id, GameService gameService) {
        Game game = getGameMap().get(id);
        return game;
    }
}