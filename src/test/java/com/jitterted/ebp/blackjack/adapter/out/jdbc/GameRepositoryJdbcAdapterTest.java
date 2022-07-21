package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Tag("integration")
@Testcontainers(disabledWithoutDocker = true)
@DataJdbcTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:14:///springboot"
})
public class GameRepositoryJdbcAdapterTest {

    @Autowired GameJdbcRepository gameJdbcRepository;

    @Test
    void newGameAfterSaveIdIsAssigned() {
        GameRepositoryJdbcAdapter adapter = new GameRepositoryJdbcAdapter(gameJdbcRepository);
        Game game = new Game(new Deck());

        Game savedGame = adapter.save(game);

        assertThat(savedGame)
                .isNotSameAs(game);
        assertThat(savedGame.getId())
                .isNotNull();
    }

    @Test
    public void savedGameIsFoundById() throws Exception {
        GameRepositoryJdbcAdapter adapter = new GameRepositoryJdbcAdapter(gameJdbcRepository);
        Game game = new Game(new Deck());
        Game savedGame = adapter.save(game);

        Optional<Game> gameById = adapter.findById(savedGame.getId());

        assertThat(gameById)
                .isPresent();
    }
}
