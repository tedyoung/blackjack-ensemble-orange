package com.jitterted.ebp.blackjack.adapter;

import com.jitterted.ebp.blackjack.application.GameIdGenerator;
import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.application.InMemoryGameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IntegrationTestConfiguration {

    @Bean
    public GameService createGameService() {
        return new GameService(new Deck(),
                               new InMemoryGameRepository(new GameIdGenerator(0L)), game -> {
        });
    }

}
