package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

import java.util.HashMap;
import java.util.Map;

public class GameService {
    private Deck deck;
    private boolean started;
    private long lastId;
    private long currentGameId;
    // GameRepository: save, findById
    private Map<Long, Game> gameMap = new HashMap<>();

    public GameService(Deck deck) {
        this.deck = deck;
    }

    public Game startGame() {
        Game game = new Game(deck);
        game.setId(idGenerator());
        currentGameId = game.getId();
        gameMap.put(game.getId(), game);
        return game;
    }

    private long idGenerator() {
        return lastId++;
    }

    public Game gameFor(long id) {
        Game game = gameMap.get(id);
        if (game == null) {
            throw new GameNotFound();
        }
        return game;
    }

    public void initialDeal(long gameId) {
        started = true;
        gameFor(gameId).initialDeal();
    }

    public void playerHits() {
        gameFor(currentGameId).playerHits();
    }

    public boolean isPlayerDone() {
        return gameFor(currentGameId).isPlayerDone();
    }

    public void playerStands() {
        gameFor(currentGameId).playerStands();
    }

    public GameOutcome gameOutcome() {
        return gameFor(currentGameId).determineOutcome();
    }
}