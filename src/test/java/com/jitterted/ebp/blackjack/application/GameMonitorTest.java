package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.FakeGameRepository;
import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameMonitorTest {

    @Test
    void playerStandsThenGameIsOverAndResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Deck deck = StubDeck.playerNotDealtBlackjackAndStands();
        GameService gameService = new GameService(deck, new FakeGameRepository(), gameMonitorSpy);
        Long gameId = gameService.createGame().getId();
        gameService.initialDeal(gameId);

        gameService.playerStands(gameId);

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    void playerHitsAndGoesBustThenResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        StubDeck deck = StubDeck.playerHitsAndGoesBust();
        GameService gameService = new GameService(deck, new FakeGameRepository(), gameMonitorSpy);
        Long gameId = gameService.createGame().getId();
        gameService.initialDeal(gameId);

        gameService.playerHits(gameId);

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    void playerHitsDoesNotBustThenNoResultsSendToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        GameService gameService = new GameService(deck, new FakeGameRepository(), gameMonitorSpy);
        Long gameId = gameService.createGame().getId();
        gameService.initialDeal(gameId);

        gameService.playerHits(gameId);

        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }

    @Test
    void playerDealtBlackjackThenResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        StubDeck deck = StubDeck.playerDealtBlackjack();
        GameService gameService = new GameService(deck, new FakeGameRepository(), gameMonitorSpy);
        Long gameId = gameService.createGame().getId();

        gameService.initialDeal(gameId);

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }


}