package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.application.GameServiceTest;
import com.jitterted.ebp.blackjack.application.port.GameRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers(disabledWithoutDocker = true)
@DataJdbcTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:14:///springboot"
})
@Import(GameRepositoryJdbcAdapter.class)
class JdbcRepositoryGameServiceTest {

    @Autowired
    GameRepository gameRepository;

    @Test
    void startGameForTheGivenDeck() throws Exception {
        new GameServiceTest()
                .startGameForTheGivenDeck(gameRepository);
    }

    @Test
    void playerStandsThenGameIsOverAndResultsSentToMonitor() throws Exception {
        new GameServiceTest()
                .playerStandsThenGameIsSaved(gameRepository);
    }

    @Test
    void afterInitialDealThenDealtCardsAreSaved() throws Exception {
        new GameServiceTest()
                .afterInitialDealThenDealtCardsAreSaved(gameRepository);
    }

    @Test
    void playerHitsThenGameIsSaved() throws Exception {
        new GameServiceTest()
                .playerHitsThenGameIsSaved(gameRepository);
    }

}
