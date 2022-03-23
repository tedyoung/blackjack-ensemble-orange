package com.jitterted.ebp.blackjack.domain.port;

import com.jitterted.ebp.blackjack.domain.Game;

public interface GameMonitor {
    void roundCompleted(Game game);
}
