package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class InMemoryGameRepositoryTest {

    @Test
    void saveGameReturnsDifferentInstanceOfSameGame() {
        GameRepository gameRepository = new InMemoryGameRepository(new GameIdGenerator(0L));
        Game game = new Game(new Deck());
        game.setId(12L);

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame)
                .isEqualTo(game);
        assertThat(savedGame)
                .isNotSameAs(game);
    }

    @Test
    void findingNotSavedGameByIdReturnsEmptyOptional() {
        GameRepository emptyGameRepository = new InMemoryGameRepository(new GameIdGenerator(0L));

        Optional<Game> foundGame = emptyGameRepository.findById(14L);

        assertThat(foundGame).isEmpty();
    }

    @Test
    void savingNewGameAssignsAnId() {
        GameRepository gameRepository = new InMemoryGameRepository(new GameIdGenerator(12L));
        Game game = new Game(new Deck());

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame.getId())
                .isNotNull();
    }

    @Test
    void savingExistingGameThatHasIdIsNotAssignedNewId() {
        GameRepository gameRepository = new InMemoryGameRepository(new GameIdGenerator(0L));
        Game game = new Game(new Deck());
        game.setId(12L);

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame.getId())
                .isEqualTo(12L);
    }

    @Test
    void findingByIdExistingGameDifferentInstanceOfSameGame() {
        GameRepository gameRepository = new InMemoryGameRepository(
                new GameIdGenerator(0L));
        Game game = new Game(new Deck());
        game.setId(12L);
        gameRepository.save(game);

        Game foundGame = gameRepository.findById(12L).get();

        assertThat(foundGame)
                .isEqualTo(game);
        assertThat(foundGame)
                .isNotSameAs(game);
    }

    @Test
    void savingGameReturnsADeepCopyOfGame() {
        GameRepository gameRepository = new InMemoryGameRepository(
                new GameIdGenerator(0L));
        Game game = new Game(new Deck());
        game.initialDeal();

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame.playerHand())
                .isEqualTo(game.playerHand());
        assertThat(savedGame.playerHand())
                .isNotSameAs(game.playerHand());
        assertThat(savedGame.dealerHand())
                .isEqualTo(game.dealerHand());
        assertThat(savedGame.dealerHand())
                .isNotSameAs(game.dealerHand());
        assertThat(savedGame.playerHand().cards())
                .isEqualTo(game.playerHand().cards());
        assertThat(savedGame.dealerHand().cards())
                .isEqualTo(game.dealerHand().cards());
        // additional assertions to ensure Decks are different instances
    }
}