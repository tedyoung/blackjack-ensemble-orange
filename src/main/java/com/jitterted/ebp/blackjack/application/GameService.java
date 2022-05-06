package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

import java.util.HashMap;
import java.util.Map;

public class GameService {
    private final GameIdGenerator gameIdGenerator;
    private Deck deck;
    // GameRepository: save, findById
    private Map<Long, Game> gameMap = new HashMap<>();

    public GameService(Deck deck, GameIdGenerator gameIdGenerator) {
        this.deck = deck;
        this.gameIdGenerator = gameIdGenerator;
    }

    public Game startGame() {
        Game game = new Game(deck);
        game.setId(gameIdGenerator.nextId());
        gameMap.put(game.getId(), game);
        return game;
    }

    public Game gameFor(long id) {
        Game game = gameMap.get(id);
        if (game == null) {
            throw new GameNotFound();
        }
        return game;
    }

    public void initialDeal(long gameId) {
        gameFor(gameId).initialDeal();
    }

    public void playerHits(long gameId) {
        gameFor(gameId).playerHits();
    }

    public boolean isPlayerDone(long gameId) {
        return gameFor(gameId).isPlayerDone();
    }

    public void playerStands(long gameId) {
        gameFor(gameId).playerStands();
    }

    public GameOutcome gameOutcome(long gameId) {
        return gameFor(gameId).determineOutcome();
    }
}