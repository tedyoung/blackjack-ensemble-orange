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

    @GetMapping("/game/{gameId}")
    public String gameView(Model model, @PathVariable Long gameId) {
        populateGameViewIn(model, gameId);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        // We want to pass an ID here, like: gameService.playerHits(gameId)
        gameService.playerHits(gameId);
        return redirectBasedOnGameState();
    }

    public String redirectBasedOnGameState() {
        if (gameService.isPlayerDone(gameId)) {
            return "redirect:/done";
        }
        return "redirect:/game/" + gameId;
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        populateGameViewIn(model, gameId);
        model.addAttribute("outcome", gameService.gameOutcome(gameId).display());
        return "done";
    }

    @PostMapping("/stand")
    public String standCommand() {
        gameService.playerStands(gameId);
        return redirectBasedOnGameState();
    }

    private void populateGameViewIn(Model model, Long gameId) {
        GameView gameView = GameView.of(gameService.gameFor(gameId));
        model.addAttribute("gameView", gameView);
        model.addAttribute("gameId", gameId);
    }

}
