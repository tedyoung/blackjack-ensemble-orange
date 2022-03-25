package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

public class GameService {
    private Game game;
    private boolean started;

    public GameService(Game game, Deck deck) {
        this.game = game;
    }

    public Game currentGame() {
        if (!started) {
            return null;
        }
        return game;
    }

    public void initialDeal() {
        started = true;
        game.initialDeal();
    }

    public void playerHits() {
        game.playerHits();
    }

    public boolean isPlayerDone() {
        return game.isPlayerDone();
    }

    public void playerStands() {
        game.playerStands();
    }

    public GameOutcome gameOutcome() {
        return game.determineOutcome();
    }
}