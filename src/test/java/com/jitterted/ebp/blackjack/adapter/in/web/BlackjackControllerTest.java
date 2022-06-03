package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.StubDeck;
import com.jitterted.ebp.blackjack.application.GameIdGenerator;
import com.jitterted.ebp.blackjack.application.GameNotFound;
import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.application.InMemoryGameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    public void startGameCreatesGameAndDoesInitialDeal() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust();
        GameService gameService = new GameService(deck, new GameIdGenerator(41), new InMemoryGameRepository());
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
        GameService gameService = new GameService(new Deck(), gameIdGenerator, new InMemoryGameRepository());
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
        GameService gameService = new GameService(deck, new GameIdGenerator(10), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();
        Game game = gameService.gameFor(10L);

        blackjackController.hitCommand(10L);

        assertThat(game.playerHand().cards())
                .hasSize(3);
        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void playerHitsGoesBustAndRedirectsToGamePage() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndGoesBust();
        GameService gameService = new GameService(deck, new GameIdGenerator(18), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand(18L);

        Game game = gameService.gameFor(18L);
        assertThat(game.isPlayerDone())
                .isTrue();
        assertThat(redirectPage)
                .isEqualTo("redirect:/game/18");
    }

    @Test
    public void playerHitsForASpecificGame() throws Exception {
        StubDeck deck = StubDeck.playerNotDealtBlackjackHitsAndGoesBust();
        GameService gameService = new GameService(deck, new GameIdGenerator(15), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        blackjackController.hitCommand(15L);

        Game game = gameService.gameFor(15L);
        assertThat(game.playerHand().cards())
                .hasSize(3);
    }

    @Test
    public void playerHitsForOneGameDoesNotAffectOtherGame() throws Exception {
        StubDeck twoGameDeckForSecondGameHit = new StubDeck(Rank.TEN, Rank.EIGHT,
                                                            Rank.SEVEN, Rank.JACK,
                                                            Rank.TEN, Rank.EIGHT,
                                                            Rank.SEVEN, Rank.JACK,
                                                            Rank.NINE);
        GameService gameService = new GameService(twoGameDeckForSecondGameHit, new GameIdGenerator(15), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();
        blackjackController.startGame(); // 16

        blackjackController.hitCommand(15L);

        Game secondGame = gameService.gameFor(16L);
        assertThat(secondGame.playerHand().cards())
                .hasSize(2);
    }

    @Test
    public void playerStandsResultsInRedirectToGamePageAndPlayerIsDone() throws Exception {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(73), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.standCommand(73L);

        assertThat(redirectPage)
                .isEqualTo("redirect:/game/73");
        Game game = gameService.gameFor(73L);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    public void beforeStartGameThenGameForIdOfZeroThrowsAnException() {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0), new InMemoryGameRepository());
        new BlackjackController(gameService);

        assertThatThrownBy(() -> gameService.gameFor(0L))
                .isInstanceOf(GameNotFound.class);
    }

    @Test
    public void afterStartGameCurrentGameHasAnId() {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        Game game = gameService.gameFor(0L);
        assertThat(game)
                .isNotNull();
    }

    @Test
    void startGameCreatesNewGame() {
        GameService gameService = new GameService(new Deck(), new GameIdGenerator(0), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        Game game = gameService.gameFor(0L);
        assertThat(game.getId())
                .isZero();
    }

    @Test
    void gameViewForDoneGameReturnsGameOverTemplateWithOutcome() {
        GameService gameService = new GameService(StubDeck.playerDealtBlackjack(), new GameIdGenerator(0), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        ConcurrentModel model = new ConcurrentModel();
        String page = blackjackController.gameView(model, 0L);

        assertThat(page)
                .isEqualTo("game-over");
        assertThat(model.containsAttribute("outcome"))
                .isTrue();
        assertThat(model.containsAttribute("gameView"))
                .isTrue();
    }

    @Test
    public void gameViewForGameInProgressReturnsGameInProgressTemplate() {
        GameService gameService = new GameService(StubDeck.playerNotDealtBlackjackHitsAndDoesNotGoBust(), new GameIdGenerator(0), new InMemoryGameRepository());
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        ConcurrentModel model = new ConcurrentModel();
        String page = blackjackController.gameView(model, 0L);

        assertThat(page)
                .isEqualTo("game-in-progress");
    }

}