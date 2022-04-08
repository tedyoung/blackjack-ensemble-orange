package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.StubDeck;
import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    public void startGameResultsInTwoCardsDealtToPlayer() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        GameService gameService = new GameService(deck);
        BlackjackController blackjackController = new BlackjackController(gameService);

        final String redirectPage = blackjackController.startGame();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");
        assertThat(gameService.currentGame().playerHand().cards())
                .hasSize(2);
    }

    @Test
    public void gameViewPopulatesViewModelWithAllCards() throws Exception {
        Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                             new Card(Suit.HEARTS, Rank.TWO),
                                             new Card(Suit.DIAMONDS, Rank.KING),
                                             new Card(Suit.CLUBS, Rank.THREE)));
        BlackjackController blackjackController = new BlackjackController(new GameService(stubDeck));
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.gameView(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");

        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");
    }

    @Test
    public void hitCommandResultsInPlayerHavingThreeCardsAndPlayerIsNotDone() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        GameService gameService = new GameService (deck);
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();
        Game game = gameService.currentGame();

        String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");

        assertThat(game.playerHand().cards())
                .hasSize(3);

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void playerHitsGoesBustAndRedirectsToDonePage() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndGoesBust();
        GameService gameService = new GameService(deck);
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand();

        assertThat(gameService.currentGame().isPlayerDone())
                .isTrue();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");
    }

    @Test
    public void donePageShowsFinalGameStateWithOutcome() throws Exception {
        GameService gameService = new GameService(new Deck());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.doneView(model);

        assertThat(model.containsAttribute("gameView"))
                .isTrue();

        String outcome = (String) model.getAttribute("outcome");
        assertThat(outcome)
                .isNotBlank();
    }

    @Test
    public void playerStandsResultsInRedirectToDonePageAndPlayerIsDone() throws Exception {
        GameService gameService = new GameService(new Deck());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.standCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");

        assertThat(gameService.currentGame().isPlayerDone())
                .isTrue();
    }

    @Test
    public void beforeStartGameCurrentGameThrowsAnException() {
        GameService gameService = new GameService(new Deck());
        new BlackjackController(gameService);

        assertThat(gameService.currentGame())
                .isNull();
    }

    @Test
    public void afterStartGameCurrentGameHasAnId() {
        GameService gameService = new GameService(new Deck());
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        assertThat(gameService.currentGame())
                .isNotNull();
    }

    @Test
    void startGameCreatesNewGame() {
        GameService gameService = new GameService(new Deck());
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        assertThat(gameService.currentGame().getId())
                .isZero();
    }
}