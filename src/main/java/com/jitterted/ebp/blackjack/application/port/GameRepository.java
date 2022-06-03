package com.jitterted.ebp.blackjack.application.port;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.Optional;

public interface GameRepository {
    Game save(Game game);

    Optional<Game> findById(long id);
}
