package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.application.GameService;
import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final GameService gameService;
    private Long gameId;

    @Autowired
    public BlackjackController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start-game")
    public String startGame() {
        Game game = gameService.startGame();
        gameId = game.getId();
        gameService.initialDeal(gameId);
        return redirectBasedOnGameState(); // also use game id here
    }

    @GetMapping("/game") // we want to add the ID here
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
        return "redirect:/game/" + gameId;
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
        GameView gameView = GameView.of(gameService.gameFor(gameId)); // gameService.gameFor(?)
        model.addAttribute("gameView", gameView);
    }

}
