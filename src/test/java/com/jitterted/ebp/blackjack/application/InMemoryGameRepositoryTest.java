package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class InMemoryGameRepositoryTest {

    @Test
    void savingGameAlreadyHasIdCanBeFoundById() {
        GameRepository gameRepository = new InMemoryGameRepository(new GameIdGenerator(0L));
        Game game = new Game(new Deck());
        game.setId(12L);

        gameRepository.save(game);

        assertThat(gameRepository.findById(12L))
                .contains(game);
    }

    @Test
    void saveGameReturnsSameGame() {
        GameRepository gameRepository = new InMemoryGameRepository(new GameIdGenerator(0L));
        Game game = new Game(new Deck());
        game.setId(12L);

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame)
                .isEqualTo(game);
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

        gameRepository.save(game);

        assertThat(gameRepository.findById(12L))
                .contains(game);
    }
}