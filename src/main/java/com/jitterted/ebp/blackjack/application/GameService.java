package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameOutcome;

import java.util.function.Consumer;

public class GameService {
    private final Deck deck;
    private final GameRepository gameRepository;
    private final GameMonitor gameMonitor;

    public GameService(Deck deck,
                       GameRepository gameRepository,
                       GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameRepository = gameRepository;
        this.gameMonitor = gameMonitor;
    }

    // FACTORY method (not a command) - these can return objects
    public Game createGame() {
        Game game = new Game(deck);
        return gameRepository.save(game);
    }

    public Game gameFor(long id) {
        return gameRepository.findById(id)
                             .orElseThrow(GameNotFound::new);
    }

    public void initialDeal(long gameId) {
        executeCommand(gameId, Game::initialDeal);
    }

    public void playerHits(long gameId) {
        executeCommand(gameId, Game::playerHits);
    }

    public void playerStands(long gameId) {
        executeCommand(gameId, Game::playerStands);
    }

    private void executeCommand(long gameId, Consumer<Game> command) {
        Game game = gameFor(gameId);
        command.accept(game);
        gameRepository.save(game);
        notifyGameMonitorUponCompletion(game);
    }

    public boolean isPlayerDone(long gameId) {
        return gameFor(gameId).isPlayerDone();
    }

    private void notifyGameMonitorUponCompletion(Game game) {
        if (isPlayerDone(game.getId())) {
            gameMonitor.roundCompleted(game);
        }
    }

    public GameOutcome gameOutcome(long gameId) {
        return gameFor(gameId).determineOutcome();
    }
}