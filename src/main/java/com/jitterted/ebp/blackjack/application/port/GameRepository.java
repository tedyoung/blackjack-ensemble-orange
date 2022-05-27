package com.jitterted.ebp.blackjack.application.port;

import com.jitterted.ebp.blackjack.domain.Game;

public interface GameRepository {
    Game save(Game game);

    Game findById(long id);
}
