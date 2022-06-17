package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.StubDeck;
import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class GameViewTest {

    @Test
    public void gameViewPopulatesViewModelWithAllCards() throws Exception {
        Deck stubDeck = new StubDeck(List.of(new Card(Rank.TEN, Suit.DIAMONDS),
                                             new Card(Rank.TWO, Suit.HEARTS),
                                             new Card(Rank.KING, Suit.DIAMONDS),
                                             new Card(Rank.THREE, Suit.CLUBS)));
        Game game = new Game(stubDeck);
        game.initialDeal();

        GameView gameView = GameView.of(game);

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");
        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");
    }

}
