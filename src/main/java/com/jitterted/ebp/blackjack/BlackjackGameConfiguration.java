package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlackjackGameConfiguration {

    @Bean
    public GameService createGameService(GameRepository gameRepository) {
        return new GameService(new Deck(), gameRepository, game -> {
        });
    }

}
