package com.jitterted.ebp.blackjack.adapter.out.jdbc;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameDboTest {

    @Test
    void givenGameTransformedToGameDbo() {
        Game game = new Game(new StubDeck(Rank.ACE, Rank.NINE,
                                          Rank.JACK, Rank.EIGHT,
                                          Rank.NINE));
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
        assertThat(gameDbo.getDeck())
                .isEqualTo("9H");
    }

    @Test
    void givenEncodedGameBeforeInitialDealDecodesGame() {
        GameDbo gameDbo = new GameDbo();
        gameDbo.setPlayerHand("");
        gameDbo.setDealerHand("");
        gameDbo.setPlayerDone(false);
        gameDbo.setDeck("AS,2H");
        gameDbo.setId(42L);

        Game game = gameDbo.toDomain();

        assertThat(game.playerHand().cards())
                .isEmpty();
        assertThat(game.dealerHand().cards())
                .isEmpty();
        assertThat(game.isPlayerDone())
                .isFalse();
        assertThat(game.deck().allCards())
                .containsExactly(
                        new Card(Rank.ACE, Suit.SPADES),
                        new Card(Rank.TWO, Suit.HEARTS));
        assertThat(game.getId())
                .isEqualTo(42L);
    }

    @Test
    public void givenEncodedGameAfterInitialDealDecodesGame() {
        GameDbo gameDbo = new GameDbo();
        gameDbo.setPlayerHand("AS,TC");
        gameDbo.setDealerHand("5H,TS");
        gameDbo.setPlayerDone(true);
        gameDbo.setDeck("AS,2H");
        gameDbo.setId(63L);

        Game game = gameDbo.toDomain();

        assertThat(game.playerHand().cards())
                .containsExactly(
                        new Card(Rank.ACE, Suit.SPADES),
                        new Card(Rank.TEN, Suit.CLUBS)
                );
        assertThat(game.dealerHand().cards())
                .containsExactly(
                        new Card(Rank.FIVE, Suit.HEARTS),
                        new Card(Rank.TEN, Suit.SPADES)
                );
        assertThat(game.isPlayerDone())
                .isTrue();
        assertThat(game.deck().allCards())
                .containsExactly(
                        new Card(Rank.ACE, Suit.SPADES),
                        new Card(Rank.TWO, Suit.HEARTS));
        assertThat(game.getId())
                .isEqualTo(63L);
    }
}
