package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

public class GameService {
    private final Deck deck;
    private final GameRepository gameRepository;
    private final GameMonitor gameMonitor;

    public GameService(Deck deck, GameRepository gameRepository, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameRepository = gameRepository;
        this.gameMonitor = gameMonitor;
    }

    public Game startGame() {
        Game game = new Game(deck);
        gameRepository.save(game);
        return game;
    }

    public Game gameFor(long id) {
        return gameRepository.findById(id)
                             .orElseThrow(GameNotFound::new);
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
        Game game = gameFor(gameId);
        game.playerStands();
        gameMonitor.roundCompleted(game);
    }

    public GameOutcome gameOutcome(long gameId) {
        return gameFor(gameId).determineOutcome();
    }
}