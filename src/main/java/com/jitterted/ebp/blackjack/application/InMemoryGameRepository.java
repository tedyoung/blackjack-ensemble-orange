package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Hand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryGameRepository implements GameRepository {
    private final Map<Long, Game> gameMap = new HashMap<>();
    private final GameIdGenerator gameIdGenerator;

    public InMemoryGameRepository(GameIdGenerator gameIdGenerator) {
        this.gameIdGenerator = gameIdGenerator;
    }

    @Override
    public Game save(Game game) {
        if (game.getId() == null) {
            game.setId(gameIdGenerator.nextId());
        }
        gameMap.put(game.getId(), game);
        return copyOf(game);
    }

    @Override
    public Optional<Game> findById(long id) {
        Game game = gameMap.get(id);
        return Optional.ofNullable(copyOf(game));
    }

    private static Game copyOf(Game game) {
        if (game == null) {
            return null;
        }

        return new Game(
                game.getId(),
                game.deck(),
                new Hand(game.playerHand().getId(), game.playerHand().cards()),
                new Hand(game.dealerHand().getId(), game.dealerHand().cards()),
                game.isPlayerDone()
        );
    }

}