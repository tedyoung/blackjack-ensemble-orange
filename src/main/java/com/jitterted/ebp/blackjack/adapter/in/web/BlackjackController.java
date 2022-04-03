package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.application.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final GameService gameService;

    @Autowired
    public BlackjackController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start-game")
    public String startGame() {
        // Goal: gameService.newGame();
        gameService.initialDeal();
        return redirectBasedOnGameState();
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        populateGameViewIn(model);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        // We want to pass an ID here, like: gameService.playerHits(gameId)
        gameService.playerHits();
        return redirectBasedOnGameState();
    }

    public String redirectBasedOnGameState() {
        if (gameService.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        populateGameViewIn(model);
        model.addAttribute("outcome", gameService.gameOutcome().display());
        return "done";
    }

    @PostMapping("/stand")
    public String standCommand() {
        gameService.playerStands();
        return redirectBasedOnGameState();
    }

    private void populateGameViewIn(Model model) {
        // We want to use gameService.gameFor(?)
        GameView gameView = GameView.of(gameService.currentGame());
        model.addAttribute("gameView", gameView);
    }

}
