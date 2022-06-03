package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class InMemoryGameRepositoryTest {

    @Test
    void givenNewUnsavedGameWhenSavedThenGameIdIsAssigned() {
        GameIdGenerator gameIdGenerator = new GameIdGenerator(55L);
        GameRepository gameRepository = new InMemoryGameRepository(gameIdGenerator);
        Game game = new Game(new Deck());

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame.getId())
                .isEqualTo(55L);
    }
}