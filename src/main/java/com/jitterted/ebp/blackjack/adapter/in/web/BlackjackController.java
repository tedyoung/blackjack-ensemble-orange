package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final GameService gameService;

    @Autowired
    public BlackjackController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start-game")
    public String startGame(String amount) {
        int betAmount;
        if (amount.isBlank()) {
            betAmount = 0;
        } else {
            betAmount = Integer.parseInt(amount);
        }

        Game game = gameService.createGame(betAmount);
        gameService.initialDeal(game.getId());
        return redirectToGamePage(game.getId());
    }


    @GetMapping("/game/{gameId}")
    public String gameView(Model model, @PathVariable Long gameId) {
        if (gameService.isPlayerDone(gameId)) {
            return gameOverView(model, gameId);
        } else {
            return gameInProgressView(model, gameId);
        }
    }

    private String gameInProgressView(Model model, Long gameId) {
        populateGameViewIn(model, gameId);
        return "game-in-progress";
    }

    private String gameOverView(Model model, Long gameId) {
        populateGameViewIn(model, gameId);
        model.addAttribute("outcome", gameService.gameOutcome(gameId).display());
        return "game-over";
    }

    private void populateGameViewIn(Model model, Long gameId) {
        GameView gameView = GameView.of(gameService.gameFor(gameId));
        model.addAttribute("gameView", gameView);
        model.addAttribute("gameId", gameId);
    }

    @PostMapping("/hit/{gameId}")
    public String hitCommand(@PathVariable Long gameId) {
        gameService.playerHits(gameId);
        return redirectToGamePage(gameId);
    }

    @PostMapping("/stand/{gameId}")
    public String standCommand(@PathVariable Long gameId) {
        gameService.playerStands(gameId);
        return redirectToGamePage(gameId);
    }

    private String redirectToGamePage(Long gameId) {
        return "redirect:/game/" + gameId;
    }
}
