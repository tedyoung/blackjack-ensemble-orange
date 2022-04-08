package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

import java.util.HashMap;
import java.util.Map;

public class GameService {
    private Game game;
    private boolean started;
    private long lastId;
    // GameRepository: save, findById
    private Map<Long, Game> gameMap = new HashMap<>();

    public GameService() {

    }

    public GameService(Game game, Deck deck) {
        this.game = game;
        gameMap.put(42L, game);
    }

    public Game startGame() {
        game = new Game(new Deck());
        game.setId(lastId++);
        gameMap.put(game.getId(), game);
        return game;
    }

    public Game currentGame() {
        if (!started) {
            return null;
        }
        return gameMap.get(42L);
    }

    public Game gameFor(long id) {
        Game game = gameMap.get(id);
        if (game == null) {
            throw new GameNotFound();
        }
        return game;
    }

    public void initialDeal() {
        started = true;
        currentGame().initialDeal();
    }

    public void playerHits() {
        currentGame().playerHits();
    }

    public boolean isPlayerDone() {
        return currentGame().isPlayerDone();
    }

    public void playerStands() {
        currentGame().playerStands();
    }

    public GameOutcome gameOutcome() {
        return currentGame().determineOutcome();
    }
}