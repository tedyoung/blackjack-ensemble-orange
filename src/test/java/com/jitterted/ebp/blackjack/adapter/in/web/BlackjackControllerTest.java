package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.StubDeck;
import com.jitterted.ebp.blackjack.application.GameIdGenerator;
import com.jitterted.ebp.blackjack.application.GameNotFound;
import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    public void startGameResultsInTwoCardsDealtToPlayer() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        GameService gameService = new GameService(deck, new GameIdGenerator(41));
        BlackjackController blackjackController = new BlackjackController(gameService);

        final String redirectPage = blackjackController.startGame();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game/41");
        Game game = gameService.gameFor(41L);
        assertThat(game.playerHand().cards())
                .hasSize(2);
    }

    @Test
    public void gameViewPopulatesViewModelWithGameViewInstanceAndGameId() throws Exception {
        GameIdGenerator gameIdGenerator = new GameIdGenerator(13L);
        GameService gameService = new GameService(new Deck(), gameIdGenerator);
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.gameView(model, 13L);

        assertThat(model.getAttribute("gameView"))
                .isInstanceOf(GameView.class);
        Long gameId = (Long) model.getAttribute("gameId");
        assertThat(gameId)
                .isEqualTo(13L);
    }


    @Test
    public void hitCommandResultsInPlayerHavingThreeCardsAndPlayerIsNotDone() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        GameService gameService = new GameService(deck, new GameIdGenerator(0));
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();
        Game game = gameService.gameFor(0L);

        String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game/0");
        assertThat(game.playerHand().cards())
                .hasSize(3);
        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void playerHitsGoesBustAndRedirectsToDonePage() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndGoesBust();
        GameService gameService = new GameService(deck, new GameIdGenerator(0));
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand();

        Game game = gameService.gameFor(0L);
        assertThat(game.isPlayerDone())
                .isTrue();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");
    }

    @Test
    public void donePageShowsFinalGameStateWithOutcome() throws Exception {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0));
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
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0));
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.standCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");

        Game game = gameService.gameFor(0L);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    public void beforeStartGameThenGameForIdOfZeroThrowsAnException() {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0));
        new BlackjackController(gameService);

        assertThatThrownBy(() -> gameService.gameFor(0L))
                .isInstanceOf(GameNotFound.class);
    }

    @Test
    public void afterStartGameCurrentGameHasAnId() {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0));
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        Game game = gameService.gameFor(0L);
        assertThat(game)
                .isNotNull();
    }

    @Test
    void startGameCreatesNewGame() {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0));
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        Game game = gameService.gameFor(0L);
        assertThat(game.getId())
                .isZero();
    }
}