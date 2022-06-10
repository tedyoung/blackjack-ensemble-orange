package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameServiceTest {

    @Test
    public void startGameForTheGivenDeck() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator));

        Game game = gameService.startGame();

        assertThat(game)
                .isNotNull();
    }

    @Test
    public void startGameCreatesNewGameEveryTime() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator));
        Game game1 = gameService.startGame();

        Game game2 = gameService.startGame();

        assertThat(game1.getId())
                .isNotEqualTo(game2.getId());
    }

    @Test
    public void getGameByIdReturnsTheCorrectGame() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator));
        Game startedGame = gameService.startGame();

        Game foundGame = gameService.gameFor(startedGame.getId());

        assertThat(foundGame)
                .isEqualTo(startedGame);
    }

    @Test
    public void getGameByIdThrowsGameNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator));

        assertThatThrownBy(() -> gameService.gameFor(0L))
                .isInstanceOf(GameNotFound.class);
    }

    @Test
    public void startMultipleGamesCanFindFirstOne() {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator));
        Game firstGame = gameService.startGame();
        gameService.startGame();
        gameService.startGame();

        Game foundGame = gameService.gameFor(firstGame.getId());

        assertThat(foundGame)
                .isEqualTo(firstGame);
    }

    @Test
    public void startGameAssignsIdFromGenerator(){
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(42L);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator));
        Game game = gameService.startGame();

        assertThat(game.getId())
                .isEqualTo(42L);
    }
}