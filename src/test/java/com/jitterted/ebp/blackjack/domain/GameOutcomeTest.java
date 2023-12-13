package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    void playerBeatsDealer() {
        Deck stubDeck = StubDeck.playerBeatsDealerUponInitialDeal();
        Game game = new Game(stubDeck);
        game.initialDeal();

        game.playerStands(); // make sure the player stands

        GameOutcome outcome = game.determineOutcome();
        assertThat(outcome.display())
                .isEqualTo("You beat the Dealer! 💵");
    }

    @Test
    void playerHitsAndGoesBustAndLoses() throws Exception {
        Deck stubDeck = StubDeck.playerHitsAndGoesBust();
        Game game = new Game(stubDeck);
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome().display())
                .isEqualTo("You Busted, so you lose.  💸");
    }

    @Test
    void playerDealtBlackjackUponInitialDealWinsAndIsDone() throws Exception {
        Deck playerDealtBlackjack = new StubDeck(Rank.ACE, Rank.NINE,
                                                 Rank.JACK, Rank.EIGHT);
        Game game = new Game(playerDealtBlackjack);

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualTo(GameOutcome.PLAYER_WINS_BLACKJACK);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void standResultsInDealerDrawingCardOnTheirTurn() throws Exception {
        Deck dealerBeatsPlayerAfterDrawingAdditionalCardDeck =
                new StubDeck(Rank.TEN,  Rank.QUEEN,
                             Rank.NINE, Rank.FIVE,
                             Rank.SIX);
        Game game = new Game(dealerBeatsPlayerAfterDrawingAdditionalCardDeck);
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }

}