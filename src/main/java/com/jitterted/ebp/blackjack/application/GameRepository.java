package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Game;

public interface GameRepository {
    Game save(Game game);

    Game findById(long id);
}
