package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.StubDeck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameDboTest {

    @Test
    void givenGameTransformedToGameDbo() {
        Game game = new Game(new StubDeck(Rank.ACE, Rank.NINE,
                                          Rank.JACK, Rank.EIGHT));
        game.setId(55L);
        game.initialDeal();

        GameDbo gameDbo = GameDbo.from(game);

        assertThat(gameDbo.getId())
                .isEqualTo(55L);
        assertThat(gameDbo.getDealerHand())
                .isEqualTo("9H,8H");
        assertThat(gameDbo.getPlayerHand())
                .isEqualTo("AH,JH");
        assertThat(gameDbo.isPlayerDone())
                .isTrue();
    }
}
