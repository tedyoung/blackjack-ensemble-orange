package com.jitterted.ebp.blackjack.application.port;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private Game game;

    @Override
    public Game save(Game game) {
        this.game = game;
        game.setId(0L);
        return game;
    }

    @Override
    public Optional<Game> findById(long id) {
        return Optional.ofNullable(game);
    }
}
