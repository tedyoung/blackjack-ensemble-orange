package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

public class GameService {
    private final Deck deck;
    private final GameRepository gameRepository;

    public GameService(Deck deck,
                       GameRepository gameRepository) {
        this.deck = deck;
        this.gameRepository = gameRepository;
    }

    public Game startGame() {
        Game game = new Game(deck);
        gameRepository.save(game);
        return game;
    }

    public Game gameFor(long id) {
        Game game = gameRepository.findById(id);
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