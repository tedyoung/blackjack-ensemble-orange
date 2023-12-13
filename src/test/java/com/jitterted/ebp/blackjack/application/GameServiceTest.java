package com.jitterted.ebp.blackjack.application;

import com.jitterted.ebp.blackjack.application.port.GameRepository;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class GameServiceTest {

    public static Stream<Arguments> repository() {
        GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        return Stream.of(
                Arguments.of(
                        new InMemoryGameRepository(gameIdGenerator)));
    }

    @ParameterizedTest
    @MethodSource("repository")
    public void startGameForTheGivenDeck(GameRepository gameRepository) throws Exception {
        GameService gameService =
                new GameService(new Deck(),
                                gameRepository, game -> {
                });

        Game game = gameService.createGame();

        assertThat(game)
                .isNotNull();
    }

    @Test
    void startGameCreatesNewGameEveryTime() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(),
                                                  new InMemoryGameRepository(gameIdGenerator), game -> {
        });
        Game game1 = gameService.createGame();

        Game game2 = gameService.createGame();

        assertThat(game1.getId())
                .isNotEqualTo(game2.getId());
    }

    @Test
    void getGameByIdReturnsTheCorrectGame() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(),
                                                  new InMemoryGameRepository(gameIdGenerator), game -> {
        });
        Game startedGame = gameService.createGame();

        Game foundGame = gameService.gameFor(startedGame.getId());

        assertThat(foundGame)
                .isEqualTo(startedGame);
    }

    @Test
    void getGameByIdThrowsGameNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator), game -> {
        });

        assertThatThrownBy(() -> gameService.gameFor(0L))
                .isInstanceOf(GameNotFound.class);
    }

    @Test
    void startMultipleGamesCanFindFirstOne() {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(0);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator), game -> {
        });
        Game firstGame = gameService.createGame();
        gameService.createGame();
        gameService.createGame();

        Game foundGame = gameService.gameFor(firstGame.getId());

        assertThat(foundGame)
                .isEqualTo(firstGame);
    }

    @Test
    void startGameAssignsIdFromGenerator() {
        final GameIdGenerator gameIdGenerator = new GameIdGenerator(42L);
        GameService gameService = new GameService(new Deck(), new InMemoryGameRepository(gameIdGenerator), game -> {
        });
        Game game = gameService.createGame();

        assertThat(game.getId())
                .isEqualTo(42L);
    }

    @ParameterizedTest
    @MethodSource("repository")
    public void playerStandsThenGameIsSaved(GameRepository gameRepository) throws Exception {
        Deck deck = StubDeck.playerNotDealtBlackjackAndStands();
        GameService gameService = new GameService(deck,
                                                  gameRepository,
                                                  game -> {});
        Long gameId = gameService.createGame().getId();
        gameService.initialDeal(gameId);

        gameService.playerStands(gameId);

        Game foundGame = gameRepository.findById(gameId).get();
        assertThat(foundGame.isPlayerDone())
                .isTrue();
    }

    @ParameterizedTest
    @MethodSource("repository")
    public void afterInitialDealThenDealtCardsAreSaved(GameRepository gameRepository) throws Exception {
        Deck deck = StubDeck.playerNotDealtBlackjackAndStands();
        GameService gameService = new GameService(deck,
                                                  gameRepository,
                                                  game -> {});
        Long gameId = gameService.createGame().getId();

        gameService.initialDeal(gameId);

        Game foundGame = gameRepository.findById(gameId).get();
        assertThat(foundGame.playerHand().cards())
                .hasSize(2);
    }

    @ParameterizedTest
    @MethodSource("repository")
    public void playerHitsThenGameIsSaved(GameRepository gameRepository) throws Exception {
        Deck deck = StubDeck.playerNotDealtBlackjackHitsAndGoesBust();
        GameService gameService = new GameService(deck,
                                                  gameRepository,
                                                  game -> {});
        Long gameId = gameService.createGame().getId();
        gameService.initialDeal(gameId);

        gameService.playerHits(gameId);

        Game foundGame = gameRepository.findById(gameId).get();
        assertThat(foundGame.playerHand().cards())
                .hasSize(3);
    }

}