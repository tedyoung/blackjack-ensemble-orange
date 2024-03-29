package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@DataJdbcTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:14:///springboot"
})
@Tag("integration")
class GameDboRepositoryTest {

    @Autowired
    GameJdbcRepository gameJdbcRepository;

    @Test
    void newGameDboAfterSaveIdIsAssigned() {
        GameDbo gameDbo = new GameDbo();
        gameDbo.setPlayerHand("AS,TC");
        gameDbo.setDealerHand("5H,TS");
        gameDbo.setPlayerDone(true);
        gameDbo.setDeck("AS,2H");
        gameDbo.setId(null);

        GameDbo saved = gameJdbcRepository.save(gameDbo);

        assertThat(saved.getId())
                .isNotNull();
    }

    @Test
    void savedGameIsFoundById() throws Exception {
        GameDbo gameDbo = new GameDbo();
        gameDbo.setPlayerHand("AS,TC");
        gameDbo.setDealerHand("5H,TS");
        gameDbo.setPlayerDone(true);
        gameDbo.setDeck("AS,2H");
        gameDbo.setId(null);

        GameDbo saved = gameJdbcRepository.save(gameDbo);

        assertThat(gameJdbcRepository.findById(saved.getId()))
                .isPresent();
    }
}
